package com.adrabazha.air.attack.alarm.system.telegram.callback;

import com.adrabazha.air.attack.alarm.system.event.AirRaidEndedEvent;
import com.adrabazha.air.attack.alarm.system.event.AirRaidStartedEvent;
import com.adrabazha.air.attack.alarm.system.model.domain.District;
import com.adrabazha.air.attack.alarm.system.model.domain.User;
import com.adrabazha.air.attack.alarm.system.model.domain.redis.DistrictState;
import com.adrabazha.air.attack.alarm.system.service.AlarmHistoryService;
import com.adrabazha.air.attack.alarm.system.service.DistrictService;
import com.adrabazha.air.attack.alarm.system.service.DistrictStateRedisService;
import com.adrabazha.air.attack.alarm.system.service.UserService;
import com.adrabazha.air.attack.alarm.system.telegram.custom.CustomInlineKeyboardButton;
import com.adrabazha.air.attack.alarm.system.telegram.wrapper.SendMessageWrapper;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.List;

@Component
public class AlarmStateChangeCallbackHandler extends BaseCallbackHandler {

    private final DistrictStateRedisService districtStateRedisService;
    private final UserService userService;
    private final DistrictService districtService;
    private final AlarmHistoryService alarmHistoryService;
    private final ApplicationEventPublisher eventPublisher;

    public AlarmStateChangeCallbackHandler(DistrictStateRedisService districtStateRedisService,
                                           UserService userService,
                                           DistrictService districtService,
                                           AlarmHistoryService alarmHistoryService,
                                           ApplicationEventPublisher eventPublisher) {
        this.districtStateRedisService = districtStateRedisService;
        this.userService = userService;
        this.districtService = districtService;
        this.alarmHistoryService = alarmHistoryService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public SendMessageWrapper handle(CallbackQuery callbackQuery, List<String> data) {
        String districtCode = data.get(0);
        DistrictState districtState = districtStateRedisService.getDistrictState(districtCode);
        districtState.toggleAlarm();

        updateAlarmHistory(districtState, callbackQuery.getFrom().getId());
        sendAirRaidNotification(districtState);

        DistrictState updatedState = districtStateRedisService.updateDistrictState(districtState);

        SendMessageWrapper wrapper = new SendMessageWrapper();
        wrapper.replyMarkupEdited(true);
        wrapper.setText(districtState.getStateMessage());
        wrapper.setChatId(callbackQuery.getMessage().getChatId().toString());
        wrapper.setMessageId(callbackQuery.getMessage().getMessageId());

        CustomInlineKeyboardButton button = new CustomInlineKeyboardButton();
        button.setText(updatedState.getAlarmState().getAlarmButtonContent());
        button.setCallbackData(updatedState.buildCallbackData());
        wrapper.addInlineButton(button);

        return wrapper;
    }

    private void sendAirRaidNotification(DistrictState districtState) {
        ApplicationEvent event;

        switch (districtState.getAlarmState()) {
            case OFF:
                event = new AirRaidEndedEvent(this, districtState.getDistrictCode());
                break;
            case ON:
                event = new AirRaidStartedEvent(this, districtState.getDistrictCode());
                break;
            default:
                throw new RuntimeException("Incorrect alarm state");
        }

        eventPublisher.publishEvent(event);
    }

    private void updateAlarmHistory(DistrictState state, Long telegramUserId) {
        User user = userService.getUserByTelegramId(telegramUserId.toString());
        District district = districtService.findByCode(state.getDistrictCode());

        alarmHistoryService.updateHistory(user, district, state.getAlarmState());
    }
}
