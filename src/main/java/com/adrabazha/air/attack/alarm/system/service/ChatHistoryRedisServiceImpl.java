package com.adrabazha.air.attack.alarm.system.service;

import com.adrabazha.air.attack.alarm.system.model.domain.redis.ChatHistory;
import com.adrabazha.air.attack.alarm.system.repository.ChatHistoryRedisRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ChatHistoryRedisServiceImpl implements ChatHistoryRedisService {

    private final ChatHistoryRedisRepository chatHistoryRedisRepository;

    public ChatHistoryRedisServiceImpl(ChatHistoryRedisRepository chatHistoryRedisRepository) {
        this.chatHistoryRedisRepository = chatHistoryRedisRepository;
    }

    @Override
    public ChatHistory getHistory(Long chatId) {
        ChatHistory chatHistory = chatHistoryRedisRepository.findById(chatId)
                .orElse(createHistory(chatId));

//        if (Objects.isNull(chatHistory.getMessageList())) {
//            chatHistory.setMessageList(new ArrayList<>());
//        }
        return chatHistory;
    }

    @Override
    public void addMessage(Long chatId, Integer messageId) {
        ChatHistory history = getHistory(chatId);
        history.addMessage(messageId);

        chatHistoryRedisRepository.save(history);
    }

    @Override
    public void clearHistory(Long chatId) {
        ChatHistory history = getHistory(chatId);
        history.getMessageList().clear();
        history.getMessageList().add(-1);

        chatHistoryRedisRepository.save(history);
    }

    private ChatHistory createHistory(Long chatId) {
        return ChatHistory.builder()
                .chatId(chatId)
                .messageList(new ArrayList<>())
                .build();
    }
}
