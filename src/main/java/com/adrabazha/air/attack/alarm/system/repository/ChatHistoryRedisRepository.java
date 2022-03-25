package com.adrabazha.air.attack.alarm.system.repository;

import com.adrabazha.air.attack.alarm.system.model.domain.redis.ChatHistory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChatHistoryRedisRepository extends CrudRepository<ChatHistory, Long> {

    List<ChatHistory> findAllByChatId(Long chatId);
}
