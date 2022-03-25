package com.adrabazha.air.attack.alarm.system.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class FunctionalButtonClickedEvent extends ApplicationEvent {

    @Getter
    private final Long chatId;

    public FunctionalButtonClickedEvent(Object source, Long chatId) {
        super(source);
        this.chatId = chatId;
    }
}
