package com.adrabazha.air.attack.alarm.system.service;

import com.adrabazha.air.attack.alarm.system.model.domain.redis.ChatHistory;

import java.util.List;
import java.util.Optional;

public interface ChatHistoryRedisService {

    ChatHistory getHistory(Long chatId);

    void addMessage(Long chatId, Integer messageId);

    void clearHistory(Long chatId);
}
