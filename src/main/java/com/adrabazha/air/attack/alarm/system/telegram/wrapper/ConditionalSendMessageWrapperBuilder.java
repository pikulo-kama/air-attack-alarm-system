package com.adrabazha.air.attack.alarm.system.telegram.wrapper;

import com.adrabazha.air.attack.alarm.system.telegram.custom.CustomInlineKeyboardButton;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ConditionalSendMessageWrapperBuilder<T> {

    private String chatId;
    private String notFoundResponseMessage;
    private String multiResponseMessageHeader;
    private Function<T, String> singleResponseMessageFunction;
    private Function<T, String> multiResponseMessageFunction;
    private Function<T, CustomInlineKeyboardButton> keyboardButtonFunction;

    public ConditionalSendMessageWrapperBuilder<T> chatId(String chatId) {
        this.chatId = chatId;
        return this;
    }

    public ConditionalSendMessageWrapperBuilder<T> notFoundResponseMessage(String notFoundMessage) {
        this.notFoundResponseMessage = notFoundMessage;
        return this;
    }

    public ConditionalSendMessageWrapperBuilder<T> provideSingleResponseMessage(Function<T, String> singleResponseMessageFunction) {
        this.singleResponseMessageFunction = singleResponseMessageFunction;
        return this;
    }

    public ConditionalSendMessageWrapperBuilder<T> provideSingleResponseKeyboardButton(Function<T, CustomInlineKeyboardButton> keyboardButtonFunction) {
        this.keyboardButtonFunction = keyboardButtonFunction;
        return this;
    }

    public ConditionalSendMessageWrapperBuilder<T> multiResponseMessageHeader(String header) {
        this.multiResponseMessageHeader = header;
        return this;
    }

    public ConditionalSendMessageWrapperBuilder<T> provideMultiResponseMessage(Function<T, String> multiResponseMessageFunction) {
        this.multiResponseMessageFunction = multiResponseMessageFunction;
        return this;
    }

    public SendMessageWrapper build(List<T> conditionalDataset) {

        SendMessageWrapper wrapper = new SendMessageWrapper();
        wrapper.setChatId(chatId);

        if (conditionalDataset.isEmpty()) {
            wrapper.setText(notFoundResponseMessage);

        } else if (conditionalDataset.size() == 1) {
            String message = singleResponseMessageFunction.apply(conditionalDataset.get(0));
            CustomInlineKeyboardButton button = keyboardButtonFunction.apply(conditionalDataset.get(0));

            wrapper.setText(message);
            wrapper.addInlineButton(button);
        } else {
            String message = conditionalDataset.stream()
                    .map(multiResponseMessageFunction)
                    .collect(Collectors.joining("\n"));

            wrapper.setText(String.format("%s%n%n%s", multiResponseMessageHeader, message));
        }
        return wrapper;
    }
}
