package com.adrabazha.air.attack.alarm.system.telegram.handler;

import com.adrabazha.air.attack.alarm.system.model.domain.redis.UserState;
import com.adrabazha.air.attack.alarm.system.service.UserService;
import com.adrabazha.air.attack.alarm.system.service.UserStateService;
import com.adrabazha.air.attack.alarm.system.telegram.processor.CommandProcessor;
import com.adrabazha.air.attack.alarm.system.telegram.proxy.CommandProcessorWrapper;
import com.adrabazha.air.attack.alarm.system.telegram.wrapper.SendMessageWrapper;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static com.adrabazha.air.attack.alarm.system.utils.MessageConstants.BUILD_WINDOW_COMMAND;
import static com.adrabazha.air.attack.alarm.system.utils.MessageConstants.START_COMMAND;

abstract class BaseTelegramInputHandler<T extends TelegramInputHandler> implements TelegramInputHandler {

    private final Map<String, CommandProcessor<T>> processors;
    protected final UserStateService userStateService;
    protected final UserService userService;

    protected BaseTelegramInputHandler(List<CommandProcessor<T>> processors,
                                       UserService userService,
                                       UserStateService userStateService) {
        this.processors = formatProcessors(processors);
        this.userStateService = userStateService;
        this.userService = userService;
    }

    @Override
    public SendMessageWrapper handle(Update update) {
        SendMessageWrapper wrapper;
        String command = update.getMessage().getText();

        if (processors.keySet().contains(command)) {
            CommandProcessorWrapper<T> processor = new CommandProcessorWrapper<>(processors.get(command), userService);
            wrapper = processor.process(update);

        } else if (isInitialCommand(command)) {
            wrapper = buildInitialResponse(update);
        } else {
            wrapper = handleDefault(update);
        }
        updateUserState(update);
        return wrapper;
    }

    protected SendMessageWrapper buildInitialResponse(Update update) {
        return buildSendMessage(update, defaultMessage());
    }

    protected abstract String defaultMessage();

    protected SendMessageWrapper handleDefault(Update update) {
        return buildSendMessage(update, "Скористайтесь меню для здійснення необхідних операцій");
    }

    @Override
    public List<KeyboardRow> getReplyKeyboard(Update update) {
        Boolean isAdmin = userService.isAdministrator(update);

        List<KeyboardRow> keyboard = new ArrayList<>();

        processors.values().forEach(processor -> {
            if (processor.isAccessRestricted() && !isAdmin) {
                return;
            }
            KeyboardRow row = new KeyboardRow();
            row.add(processor.getCommandName());
            keyboard.add(row);
        });
        return keyboard;
    }

    @Override
    public Optional<Class<? extends TelegramInputHandler>> getRedirectCommand(Update update) {
        Optional<Class<? extends TelegramInputHandler>> optionalClass = Optional.empty();
        String command = update.getMessage().getText();

        if (processors.keySet().contains(command)) {
            CommandProcessor<T> processor = processors.get(command);
            optionalClass = Optional.ofNullable(processor.getRedirectToHandler());
        }
        return optionalClass;
    }

    private void updateUserState(Update update) {
        UserState state = userStateService.getOrCreateState(update.getMessage().getFrom().getId().toString());
        state.setCurrentHandler(this.getClass().getName());
        userStateService.updateState(state);
    }

    private Map<String, CommandProcessor<T>> formatProcessors(List<CommandProcessor<T>> processors) {
        Map<String, CommandProcessor<T>> processorMap = processors.stream()
                .collect(Collectors.toMap(
                        CommandProcessor::getCommandName,
                        processor -> processor
                ));

        Map<String, CommandProcessor<T>> sortedProcessorMap = new TreeMap<>(
                Comparator.comparingInt(key -> {
                    Optional<CommandProcessor<T>> processor = Optional.ofNullable(processorMap.get(key));
                    return processor.isPresent() ? processor.get().getPosition() : 0;
                })
        );
        sortedProcessorMap.putAll(processorMap);
        return sortedProcessorMap;
    }

    private SendMessageWrapper buildSendMessage(Update update, String message) {
        SendMessageWrapper wrapper = new SendMessageWrapper();
        wrapper.setChatId(update.getMessage().getChatId().toString());
        wrapper.setText(message);
        return wrapper;
    }

    private boolean isInitialCommand(String command) {
        return command.equals(BUILD_WINDOW_COMMAND) || command.equals(START_COMMAND);
    }
}
