package com.adrabazha.air.attack.alarm.system.telegram.handler;

import com.adrabazha.air.attack.alarm.system.telegram.wrapper.SendMessageWrapper;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface TelegramInputHandler {

    SendMessageWrapper handle(Update update);

    List<KeyboardRow> getReplyKeyboard(Update update);

    default Boolean hasPredecessor() {
        return Objects.nonNull(getPredecessor());
    }

    default Class<? extends TelegramInputHandler> getPredecessor() {
        return null;
    }

    Optional<Class<? extends TelegramInputHandler>> getRedirectCommand(Update update);
}
