package com.adrabazha.air.attack.alarm.system.telegram.processor.main_menu;

import com.adrabazha.air.attack.alarm.system.telegram.handler.AlertSubscriptionHandler;
import com.adrabazha.air.attack.alarm.system.telegram.handler.MainMenuHandler;
import com.adrabazha.air.attack.alarm.system.telegram.handler.TelegramInputHandler;
import com.adrabazha.air.attack.alarm.system.telegram.processor.CommandProcessor;
import com.adrabazha.air.attack.alarm.system.telegram.wrapper.SendMessageWrapper;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class AddSubscriptionCommandProcessor implements CommandProcessor<MainMenuHandler> {

    @Override
    public SendMessageWrapper process(Update update) {
        return null;
    }

    @Override
    public String getCommandName() {
        return "Хочу отримувати сповіщення про тривогу :e-mail: :hand_with_index_and_middle_fingers_crossed:";
    }

    @Override
    public Boolean isAccessRestricted() {
        return false;
    }

    @Override
    public Integer getPosition() {
        return 1;
    }

    @Override
    public Class<? extends TelegramInputHandler> getRedirectToHandler() {
        return AlertSubscriptionHandler.class;
    }
}
