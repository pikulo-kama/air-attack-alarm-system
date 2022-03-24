package com.adrabazha.air.attack.alarm.system.telegram.proxy;

import com.adrabazha.air.attack.alarm.system.telegram.wrapper.SendMessageWrapper;
import com.adrabazha.air.attack.alarm.system.service.UserService;
import com.adrabazha.air.attack.alarm.system.telegram.handler.TelegramInputHandler;
import com.adrabazha.air.attack.alarm.system.telegram.processor.CommandProcessor;
import org.telegram.telegrambots.meta.api.objects.Update;

public class CommandProcessorWrapper<H extends TelegramInputHandler> implements CommandProcessor<H> {

    private final CommandProcessor<H> commandProcessor;
    private final UserService userService;

    public CommandProcessorWrapper(CommandProcessor<H> commandProcessor, UserService userService) {
        this.commandProcessor = commandProcessor;
        this.userService = userService;
    }

    @Override
    public SendMessageWrapper process(Update update) {
        if (isAccessRestricted(update)) {
            return buildAccessRestrictedMessage(update);
        }
        return commandProcessor.process(update);
    }

    @Override
    public String getCommandName() {
        return commandProcessor.getCommandName();
    }

    @Override
    public Boolean isAccessRestricted() {
        return commandProcessor.isAccessRestricted();
    }

    @Override
    public Integer getPosition() {
        return commandProcessor.getPosition();
    }

    private boolean isAccessRestricted(Update update) {
        return isAccessRestricted() && !userService.isAdministrator(update);
    }

    private SendMessageWrapper buildAccessRestrictedMessage(Update update) {
        SendMessageWrapper wrapper = new SendMessageWrapper();
        wrapper.setChatId(update.getMessage().getChatId().toString());
        wrapper.setText("Ви не маєте необхідних прав щоб здійснити цю операцію");
        return wrapper;
    }
}
