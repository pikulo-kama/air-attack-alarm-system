package com.adrabazha.air.attack.alarm.system.telegram.callback;

import com.adrabazha.air.attack.alarm.system.model.domain.redis.DistrictState;
import com.adrabazha.air.attack.alarm.system.service.DistrictStateService;
import com.adrabazha.air.attack.alarm.system.telegram.wrapper.SendMessageWrapper;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

@Component
public class ChangeAlarmStateCallbackQueryHandler extends BaseCallbackQueryHandler {

    private final DistrictStateService districtStateService;

    public ChangeAlarmStateCallbackQueryHandler(DistrictStateService districtStateService) {
        this.districtStateService = districtStateService;
    }

    @Override
    public SendMessageWrapper handle(CallbackQuery callbackQuery, List<String> data) {
        String districtCode = data.get(0);
        DistrictState districtState = districtStateService.getDistrictState(districtCode);
        districtState.toggleAlarm();

        DistrictState updatedState = districtStateService.updateDistrictState(districtState);

        SendMessageWrapper wrapper = new SendMessageWrapper();
        wrapper.replyMarkupEdited(true);
        wrapper.setText(districtState.getStateMessage());
        wrapper.setChatId(callbackQuery.getMessage().getChatId().toString());
        wrapper.setMessageId(callbackQuery.getMessage().getMessageId());

        InlineKeyboardButton button = new InlineKeyboardButton(updatedState.getAlarmState().getAlarmButtonContent());
        button.setCallbackData(updatedState.buildCallbackData());
        wrapper.addInlineButton(button);

        return wrapper;
    }
}
