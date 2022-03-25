package com.adrabazha.air.attack.alarm.system.listener;

import com.adrabazha.air.attack.alarm.system.event.FunctionalButtonClickedEvent;
import com.adrabazha.air.attack.alarm.system.model.domain.redis.ChatHistory;
import com.adrabazha.air.attack.alarm.system.service.ChatHistoryRedisService;
import com.adrabazha.air.attack.alarm.system.telegram.AirRaidBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Slf4j
public class OnFunctionalButtonClickedEventListener
        implements ApplicationListener<FunctionalButtonClickedEvent> {

    private final ChatHistoryRedisService chatHistoryRedisService;
    private final AirRaidBot airRaidBot;

    public OnFunctionalButtonClickedEventListener(ChatHistoryRedisService chatHistoryRedisService, AirRaidBot airRaidBot) {
        this.chatHistoryRedisService = chatHistoryRedisService;
        this.airRaidBot = airRaidBot;
    }

    @Override
    public void onApplicationEvent(FunctionalButtonClickedEvent event) {
        ChatHistory history = chatHistoryRedisService.getHistory(event.getChatId());

        history.getMessageList().forEach(messageId -> {
            try {
                airRaidBot.executeAsync(
                        DeleteMessage.builder()
                            .chatId(event.getChatId().toString())
                            .messageId(messageId)
                            .build()
                );
            } catch (TelegramApiException e) {
                log.error(e.getMessage());
            }
        });

        chatHistoryRedisService.clearHistory(event.getChatId());
    }
}
