package com.adrabazha.air.attack.alarm.system.model.domain.redis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@RedisHash("TelegramMessage")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatHistory {

    @Id
    private Long chatId;

    private List<Integer> messageList;

    public void addMessage(Integer messageId) {
        messageList.add(messageId);
    }
}
