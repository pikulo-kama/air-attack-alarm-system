package com.adrabazha.air.attack.alarm.system.telegram.processor.main_menu;

import com.adrabazha.air.attack.alarm.system.model.domain.redis.UserState;
import com.adrabazha.air.attack.alarm.system.service.UserStateService;
import com.adrabazha.air.attack.alarm.system.telegram.handler.MainMenuHandler;
import com.adrabazha.air.attack.alarm.system.telegram.handler.SubmitAdministrationRequestHandler;
import com.adrabazha.air.attack.alarm.system.telegram.handler.TelegramInputHandler;
import com.adrabazha.air.attack.alarm.system.telegram.processor.CommandProcessor;
import com.adrabazha.air.attack.alarm.system.telegram.wrapper.SendMessageWrapper;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class AdminRequestCommandProcessor implements CommandProcessor<MainMenuHandler> {

    private final UserStateService userStateService;
    public static final String COMMAND_NAME = "Хочу повідомляти про небезпеку \uD83E\uDD20";

    public AdminRequestCommandProcessor(UserStateService userStateService) {
        this.userStateService = userStateService;
    }

    @Override
    public SendMessageWrapper process(Update update) {
        SendMessageWrapper wrapper = new SendMessageWrapper();
        wrapper.setChatId(update.getMessage().getChatId().toString());

        String message = "Хочете допомогти країні і повідомляти людей про небезпеку?\n\n" +
                "Гаразд! Давайте спробуєм, залиште повідомлення з метою вашого запиту," +
                " та вкажіть скільки часу ви готові приділяти цій справі.";

        wrapper.setText(message);

        UserState userState = userStateService.getOrCreateState(update.getMessage().getFrom().getId().toString());
        userState.setCurrentHandler(SubmitAdministrationRequestHandler.class.getName());
        userStateService.updateState(userState);

        return wrapper;
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public Boolean isAccessRestricted() {
        return false;
    }

    @Override
    public Integer getPosition() {
        return 3;
    }

    @Override
    public Class<? extends TelegramInputHandler> getRedirectToHandler() {
        return SubmitAdministrationRequestHandler.class;
    }
}
