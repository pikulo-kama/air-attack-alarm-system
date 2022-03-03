package com.adrabazha.air.attack.alarm.system.telegram.service;

import com.adrabazha.air.attack.alarm.system.telegram.wrapper.SendMessageWrapper;
import org.telegram.telegrambots.meta.api.methods.CopyMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface TelegramDistrictService {

    default SendMessageWrapper buildDistrictStateResponse(Update update) {
        Message message = update.getMessage();
        return buildDistrictStateResponse(message.getChatId().toString(), message.getText());
    }

    SendMessageWrapper buildDistrictStateResponse(String chatId, String input);

}
