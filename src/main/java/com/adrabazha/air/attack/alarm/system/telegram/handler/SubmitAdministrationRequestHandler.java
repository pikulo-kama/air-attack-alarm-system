package com.adrabazha.air.attack.alarm.system.telegram.handler;

import com.adrabazha.air.attack.alarm.system.model.domain.User;
import com.adrabazha.air.attack.alarm.system.service.UserService;
import com.adrabazha.air.attack.alarm.system.service.UserStateService;
import com.adrabazha.air.attack.alarm.system.telegram.processor.CommandProcessor;
import com.adrabazha.air.attack.alarm.system.telegram.wrapper.SendMessageWrapper;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static java.lang.String.format;

@Component
public class SubmitAdministrationRequestHandler extends BaseTelegramInputHandler<SubmitAdministrationRequestHandler> {

    protected SubmitAdministrationRequestHandler(
            List<CommandProcessor<SubmitAdministrationRequestHandler>> commandProcessors,
            UserService userService,
            UserStateService userStateService) {
        super(commandProcessors, userService, userStateService);
    }

    @Override
    protected SendMessageWrapper handleDefault(Update update) {
        SendMessageWrapper wrapper = new SendMessageWrapper();
        wrapper.setChatId(update.getMessage().getChatId().toString());

        User persistedUser = userService.findOrCreate(update);

        Long userId = persistedUser.getId();
        if (persistedUser.getAdministrationRequestSent()) {
            wrapper.setText(format("Ваша заявка розглядається \uD83E\uDDD0%nНомер вашої заявки - *#%d*", userId));
        } else {
            persistedUser.setAdministrationRequestSent(true);
            persistedUser.setRequestMessage(update.getMessage().getText());

            userService.updateUser(persistedUser);
            wrapper.setText(format("Спасибі! Ваш запит прийнято, ось номер вашої заявки - *#%d*", userId));
        }
        return wrapper;
    }

    @Override
    protected String defaultMessage() {
        return "Скажіть пару слів про себе, та чим ви займаєтесь";
    }

    @Override
    public Class<? extends TelegramInputHandler> getPredecessor() {
        return MainMenuHandler.class;
    }
}
