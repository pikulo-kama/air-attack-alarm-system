package com.adrabazha.air.attack.alarm.system.telegram;

import com.adrabazha.air.attack.alarm.system.telegram.callback.CallbackQueryHandler;
import com.adrabazha.air.attack.alarm.system.telegram.wrapper.SendMessageWrapper;
import com.adrabazha.air.attack.alarm.system.utils.CallbackDataHelper;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class TelegramCallbackQueryFacade {

    private final Map<? extends Class<? extends CallbackQueryHandler>, CallbackQueryHandler> callbackHandlers;

    public TelegramCallbackQueryFacade(List<CallbackQueryHandler> queryHandlers) {
        callbackHandlers = queryHandlers.stream()
                .collect(Collectors.toMap(CallbackQueryHandler::getClass, handler -> handler));
    }

    public SendMessageWrapper handle(Update update) {
        Class<?> handlerClassName = CallbackDataHelper.getQueryHandler(update.getCallbackQuery().getData());

        CallbackQueryHandler queryHandler = callbackHandlers.get(handlerClassName);
        return queryHandler.handle(update);
    }
}
