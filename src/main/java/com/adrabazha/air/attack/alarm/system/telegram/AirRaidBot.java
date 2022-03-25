package com.adrabazha.air.attack.alarm.system.telegram;

import com.adrabazha.air.attack.alarm.system.service.ChatHistoryRedisService;
import com.adrabazha.air.attack.alarm.system.telegram.custom.CustomEditMessageText;
import com.adrabazha.air.attack.alarm.system.telegram.custom.CustomSendMessage;
import com.adrabazha.air.attack.alarm.system.telegram.custom.ExecuteAsyncMessageCallback;
import com.adrabazha.air.attack.alarm.system.telegram.wrapper.SendMessageWrapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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

    private final ExecuteAsyncMessageCallback callback;

    private final ChatHistoryRedisService chatHistoryRedisService;

    public AirRaidBot(TelegramUpdateFacade telegramUpdateFacade,
                      TelegramCallbackQueryFacade telegramCallbackQueryFacade,
                      ExecuteAsyncMessageCallback callback,
                      ChatHistoryRedisService chatHistoryRedisService) {
        this.telegramUpdateFacade = telegramUpdateFacade;
        this.telegramCallbackQueryFacade = telegramCallbackQueryFacade;
        this.callback = callback;
        this.chatHistoryRedisService = chatHistoryRedisService;
    }

    @Override
    @SneakyThrows
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            return handleCallbackQuery(update);
        }
        chatHistoryRedisService.addMessage(update.getMessage().getChatId(), update.getMessage().getMessageId());

        SendMessageWrapper wrapper = telegramUpdateFacade.handle(update);
        sendKeyboard(wrapper);


        executeAsync(wrapper.build(), callback);
        return null;
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
        CustomSendMessage message = new CustomSendMessage();
        message.setChatId(wrapper.getChatId());
        message.setText(":ua:");
        message.setReplyMarkup(ReplyKeyboardMarkup.builder()
                        .keyboard(wrapper.getReplyKeyboard())
                        .build());
        try {
            executeAsync(message, callback);
        } catch (TelegramApiException exception) {
            log.error(exception.getMessage());
        }
    }

    private EditMessageText getEditMessage(SendMessageWrapper wrapper) {
        CustomEditMessageText editMessage = new CustomEditMessageText();
        editMessage.setChatId(wrapper.getChatId());

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(wrapper.getInlineKeyboard());
        editMessage.setReplyMarkup(markup);

        editMessage.setMessageId(wrapper.getMessageId());
        editMessage.setText(wrapper.getText());

        return editMessage;
    }
}
