package com.adrabazha.air.attack.alarm.system.telegram.proxy;

import com.adrabazha.air.attack.alarm.system.telegram.handler.TelegramInputHandler;
import com.adrabazha.air.attack.alarm.system.telegram.wrapper.SendMessageWrapper;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;
import java.util.Optional;

import static com.adrabazha.air.attack.alarm.system.utils.MessageConstants.RETURN_COMMAND;

public class HandlerWrapper implements TelegramInputHandler {

    private final TelegramInputHandler handler;

    public HandlerWrapper(TelegramInputHandler handler) {
        this.handler = handler;
    }

    @Override
    public SendMessageWrapper handle(Update update) {
        SendMessageWrapper wrapper = handler.handle(update);
        List<KeyboardRow> keyboard = handler.getReplyKeyboard(update);

        if (handler.hasPredecessor()) {
            KeyboardRow returnButtonRow = new KeyboardRow(List.of(new KeyboardButton(RETURN_COMMAND)));
            keyboard.add(returnButtonRow);
        }

        wrapper.setReplyKeyboard(keyboard);
        return wrapper;
    }

    @Override
    public List<KeyboardRow> getReplyKeyboard(Update update) {
        return handler.getReplyKeyboard(update);
    }

    @Override
    public Class<? extends TelegramInputHandler> getPredecessor() {
        return handler.getPredecessor();
    }

    @Override
    public Optional<Class<? extends TelegramInputHandler>> getRedirectCommand(Update update) {
        return handler.getRedirectCommand(update);
    }
}
