package com.adrabazha.air.attack.alarm.system.telegram.custom;

import com.adrabazha.air.attack.alarm.system.service.ChatHistoryRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.updateshandlers.SentCallback;

@Slf4j
@Component
public class ExecuteAsyncMessageCallback implements SentCallback<Message> {

    private final ChatHistoryRedisService chatHistoryRedisService;

    public ExecuteAsyncMessageCallback(ChatHistoryRedisService chatHistoryRedisService) {
        this.chatHistoryRedisService = chatHistoryRedisService;
    }

    @Override
    public void onResult(BotApiMethod<Message> method, Message response) {
        chatHistoryRedisService.addMessage(response.getChatId(), response.getMessageId());
    }

    @Override
    public void onError(BotApiMethod<Message> method, TelegramApiRequestException apiException) {
        log.error(apiException.getMessage());
    }

    @Override
    public void onException(BotApiMethod<Message> method, Exception exception) {
        log.error(exception.getMessage());
    }
}
