package com.adrabazha.air.attack.alarm.system.telegram.processor;

import com.adrabazha.air.attack.alarm.system.telegram.wrapper.SendMessageWrapper;
import com.adrabazha.air.attack.alarm.system.telegram.handler.TelegramInputHandler;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface CommandProcessor<H extends TelegramInputHandler> {

    SendMessageWrapper process(Update update);

    String getCommandName();

    Boolean isAccessRestricted();

    Integer getPosition();

    default Class<? extends TelegramInputHandler> getRedirectToHandler() {
        return null;
    }
}
