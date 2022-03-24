package com.adrabazha.air.attack.alarm.system.telegram.handler;

import com.adrabazha.air.attack.alarm.system.model.domain.redis.DistrictState;
import com.adrabazha.air.attack.alarm.system.service.UserService;
import com.adrabazha.air.attack.alarm.system.service.UserStateService;
import com.adrabazha.air.attack.alarm.system.telegram.custom.CustomInlineKeyboardButton;
import com.adrabazha.air.attack.alarm.system.telegram.processor.CommandProcessor;
import com.adrabazha.air.attack.alarm.system.telegram.service.TelegramDistrictService;
import com.adrabazha.air.attack.alarm.system.telegram.wrapper.ConditionalSendMessageWrapperBuilder;
import com.adrabazha.air.attack.alarm.system.telegram.wrapper.SendMessageWrapper;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static java.lang.String.format;

@Component
public class DistrictSearchInputHandler extends BaseTelegramInputHandler<DistrictSearchInputHandler> {

    private final TelegramDistrictService telegramDistrictService;

    public DistrictSearchInputHandler(
            List<CommandProcessor<DistrictSearchInputHandler>> processors,
            UserStateService userStateService,
            UserService userService, TelegramDistrictService telegramDistrictService) {
        super(processors, userService, userStateService);
        this.telegramDistrictService = telegramDistrictService;
    }

    @Override
    protected String defaultMessage() {
        return "Введіть назву області або її частину щоб переглянути" +
                " її поточний стан :ua: :raised_hands:";
    }

    @Override
    protected SendMessageWrapper handleDefault(Update update) {
        List<DistrictState> matchingDistricts = telegramDistrictService.getDistrictStatesByInput(update.getMessage().getText());

        return new ConditionalSendMessageWrapperBuilder<DistrictState>()
                .chatId(update.getMessage().getChatId().toString())
                .notFoundResponseMessage(":thinking: За вашим запитом нічого не знайдено\nСпробуйте ще раз")

                .provideSingleResponseMessage(DistrictState::getStateMessage)
                .provideSingleResponseKeyboardButton(state -> {
                    CustomInlineKeyboardButton button = new CustomInlineKeyboardButton();
                    button.setText(state.getAlarmState().getAlarmButtonContent());
                    button.setCallbackData(state.buildCallbackData());
                    return button;
                })

                .multiResponseMessageHeader(":thinking: Який саме обласний центр вас цікавить?")
                .provideMultiResponseMessage(state -> format("%s %s", state.getAlarmState().getEmoji(), state.getDistrictName()))

                .build(matchingDistricts);
    }

    @Override
    public Class<? extends TelegramInputHandler> getPredecessor() {
        return MainMenuHandler.class;
    }
}
