package com.adrabazha.air.attack.alarm.system.telegram.wrapper;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class SendMessageWrapper {

    @Getter @Setter
    private List<KeyboardRow> replyKeyboard;

    private Boolean replyMarkupEdited;

    @Getter @Setter
    private String chatId;

    @Getter @Setter
    private Integer messageId;

    @Getter @Setter
    private String text;

    @Getter
    private final List<List<InlineKeyboardButton>> inlineKeyboard;

    public SendMessageWrapper() {
        this.replyKeyboard = new ArrayList<>();
        this.inlineKeyboard = new ArrayList<>();
    }

    public Boolean isReplyMarkupEdited() {
        return replyMarkupEdited;
    }

    public void replyMarkupEdited(Boolean isEdited) {
        replyMarkupEdited = isEdited;
    }

    public void addInlineButton(InlineKeyboardButton button) {
        addInlineButton(Collections.singletonList(button));
    }

    public void addInlineButton(List<InlineKeyboardButton> buttons) {
        inlineKeyboard.add(buttons);
    }

    public SendMessage build() {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);

        sendMessage.setText(text);
        sendMessage.setChatId(chatId);

        InlineKeyboardMarkup inlineMarkup = new InlineKeyboardMarkup();
        inlineMarkup.setKeyboard(inlineKeyboard);
        sendMessage.setReplyMarkup(inlineMarkup);

        return sendMessage;
    }
}
