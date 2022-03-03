package com.adrabazha.air.attack.alarm.system.telegram;

import com.adrabazha.air.attack.alarm.system.telegram.wrapper.SendMessageWrapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Service
@Slf4j
public class AirRaidBot extends TelegramWebhookBot {

    @Value("${telegram.token}")
    private String telegramToken;

    @Value("${telegram.bot-username}")
    private String botUsername;

    @Value("${telegram.webhook-path}")
    private String botPath;

    private final TelegramUpdateFacade telegramUpdateFacade;

    private final TelegramCallbackQueryFacade telegramCallbackQueryFacade;

    public AirRaidBot(TelegramUpdateFacade telegramUpdateFacade, TelegramCallbackQueryFacade telegramCallbackQueryFacade) {
        this.telegramUpdateFacade = telegramUpdateFacade;
        this.telegramCallbackQueryFacade = telegramCallbackQueryFacade;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            return handleCallbackQuery(update);
        }

        deleteMessageFromChat(update);

        SendMessageWrapper wrapper = telegramUpdateFacade.handle(update);
        SendMessage message = wrapper.build();
        sendKeyboard(wrapper);

        return message;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return telegramToken;
    }

    @Override
    public String getBotPath() {
        return botPath;
    }

    private BotApiMethod<?> handleCallbackQuery(Update update) {
        SendMessageWrapper wrapper = telegramCallbackQueryFacade.handle(update);
        if (wrapper.isReplyMarkupEdited()) {
            updateReplyMarkup(wrapper);
            return null;
        }
        return wrapper.build();
    }

    @SneakyThrows
    private void updateReplyMarkup(SendMessageWrapper wrapper) {
        executeAsync(getEditMessage(wrapper));
    }

    private void sendKeyboard(SendMessageWrapper wrapper) {
        List<KeyboardRow> replyKeyboard = wrapper.getReplyKeyboard();
        SendMessage message = SendMessage.builder()
                .protectContent(true)
                .chatId(wrapper.getChatId())
                .text("\uD83C\uDDFA\uD83C\uDDE6")
                .replyMarkup(ReplyKeyboardMarkup.builder()
                        .keyboard(replyKeyboard)
                        .build())
                .build();
        try {
            executeAsync(message);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    @SneakyThrows
    private void deleteMessageFromChat(Update update) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(update.getMessage().getChatId().toString());
        deleteMessage.setMessageId(update.getMessage().getMessageId());

        executeAsync(deleteMessage);
    }

    private EditMessageText getEditMessage(SendMessageWrapper wrapper) {
        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(wrapper.getChatId());

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(wrapper.getInlineKeyboard());
        editMessage.setReplyMarkup(markup);

        editMessage.setMessageId(wrapper.getMessageId());
        editMessage.setText(wrapper.getText());

        return editMessage;
    }
}
