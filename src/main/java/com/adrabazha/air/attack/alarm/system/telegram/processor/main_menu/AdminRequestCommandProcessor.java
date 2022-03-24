package com.adrabazha.air.attack.alarm.system.telegram.processor.main_menu;

import com.adrabazha.air.attack.alarm.system.telegram.handler.MainMenuHandler;
import com.adrabazha.air.attack.alarm.system.telegram.handler.SubmitAdministrationRequestHandler;
import com.adrabazha.air.attack.alarm.system.telegram.handler.TelegramInputHandler;
import com.adrabazha.air.attack.alarm.system.telegram.processor.CommandProcessor;
import com.adrabazha.air.attack.alarm.system.telegram.wrapper.SendMessageWrapper;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class AdminRequestCommandProcessor implements CommandProcessor<MainMenuHandler> {

    public static final String COMMAND_NAME = "Хочу повідомляти про небезпеку :cowboy:";

    @Override
    public SendMessageWrapper process(Update update) {
        return null;
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public Boolean isAccessRestricted() {
        return false;
    }

    @Override
    public Integer getPosition() {
        return 3;
    }

    @Override
    public Class<? extends TelegramInputHandler> getRedirectToHandler() {
        return SubmitAdministrationRequestHandler.class;
    }
}
