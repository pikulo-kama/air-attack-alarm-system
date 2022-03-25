package com.adrabazha.air.attack.alarm.system.telegram;

import com.adrabazha.air.attack.alarm.system.service.UserStateRedisService;
import com.adrabazha.air.attack.alarm.system.telegram.wrapper.SendMessageWrapper;
import com.adrabazha.air.attack.alarm.system.model.domain.redis.UserState;
import com.adrabazha.air.attack.alarm.system.telegram.handler.TelegramInputHandler;
import com.adrabazha.air.attack.alarm.system.telegram.proxy.HandlerWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.adrabazha.air.attack.alarm.system.utils.MessageConstants.BUILD_WINDOW_COMMAND;
import static com.adrabazha.air.attack.alarm.system.utils.MessageConstants.RETURN_COMMAND;

@Component
@Slf4j
public class TelegramUpdateFacade {

    private final UserStateRedisService userStateRedisService;
    private final Map<? extends Class<? extends TelegramInputHandler>, TelegramInputHandler> handlers;

    public TelegramUpdateFacade(UserStateRedisService userStateRedisService, List<TelegramInputHandler> handlers) {
        this.userStateRedisService = userStateRedisService;
        this.handlers = handlers.stream().collect(Collectors.toMap(TelegramInputHandler::getClass, handler -> handler));
    }

    public SendMessageWrapper handle(Update update) {
        SendMessageWrapper wrappedMessage = null;
        UserState userState = userStateRedisService.getOrCreateState(update.getMessage().getFrom().getId().toString());

        try {
            Class<?> handlerClassName = Class.forName(userState.getCurrentHandler());
            TelegramInputHandler handler = handlers.get(handlerClassName);

            if (isReturnCommand(update) && handler.hasPredecessor()) {
                handler = handlers.get(handler.getPredecessor());
                setBuildWindowCommand(update);
            }
            Optional<Class<? extends TelegramInputHandler>> redirect = handler.getRedirectCommand(update);

            if (redirect.isPresent()) {
                handler = handlers.get(redirect.get());
                setBuildWindowCommand(update);
            }

            HandlerWrapper wrapper = new HandlerWrapper(handler);
            wrappedMessage = wrapper.handle(update);

        } catch (ClassNotFoundException exception) {
            log.error(exception.getMessage());
        }
        return wrappedMessage;
    }

    private void setBuildWindowCommand(Update update) {
        update.getMessage().setText(BUILD_WINDOW_COMMAND);
    }

    private Boolean isReturnCommand(Update update) {
        return Objects.equals(update.getMessage().getText(), RETURN_COMMAND);
    }
}
