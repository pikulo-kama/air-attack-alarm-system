package com.adrabazha.air.attack.alarm.system.telegram;

import com.adrabazha.air.attack.alarm.system.telegram.callback.CallbackHandler;
import com.adrabazha.air.attack.alarm.system.telegram.wrapper.SendMessageWrapper;
import com.adrabazha.air.attack.alarm.system.utils.CallbackDataHelper;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class TelegramCallbackQueryFacade {

    private final Map<? extends Class<? extends CallbackHandler>, CallbackHandler> callbackHandlers;

    public TelegramCallbackQueryFacade(List<CallbackHandler> queryHandlers) {
        callbackHandlers = queryHandlers.stream()
                .collect(Collectors.toMap(CallbackHandler::getClass, handler -> handler));
    }

    public SendMessageWrapper handle(Update update) {
        Class<?> handlerClassName = CallbackDataHelper.getQueryHandler(update.getCallbackQuery().getData());

        CallbackHandler queryHandler = callbackHandlers.get(handlerClassName);
        return queryHandler.handle(update);
    }
}
