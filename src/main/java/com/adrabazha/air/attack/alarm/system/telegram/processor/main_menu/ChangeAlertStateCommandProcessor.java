package com.adrabazha.air.attack.alarm.system.telegram.processor.main_menu;

import com.adrabazha.air.attack.alarm.system.telegram.handler.DistrictSearchInputHandler;
import com.adrabazha.air.attack.alarm.system.telegram.handler.TelegramInputHandler;
import com.adrabazha.air.attack.alarm.system.telegram.wrapper.SendMessageWrapper;
import com.adrabazha.air.attack.alarm.system.telegram.handler.MainMenuHandler;
import com.adrabazha.air.attack.alarm.system.telegram.processor.CommandProcessor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class ChangeAlertStateCommandProcessor implements CommandProcessor<MainMenuHandler> {

    @Override
    public SendMessageWrapper process(Update update) {
        return null;
    }

    @Override
    public String getCommandName() {
        return "\uD83C\uDF0B Оновити дані про стан сирен \uD83D\uDDFB";
    }

    @Override
    public Boolean isAccessRestricted() {
        return true;
    }

    @Override
    public Integer getPosition() {
        return 1;
    }

    @Override
    public Class<? extends TelegramInputHandler> getRedirectToHandler() {
        return DistrictSearchInputHandler.class;
    }
}
