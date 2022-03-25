package com.adrabazha.air.attack.alarm.system.telegram.handler;

import com.adrabazha.air.attack.alarm.system.model.domain.User;
import com.adrabazha.air.attack.alarm.system.service.UserService;
import com.adrabazha.air.attack.alarm.system.service.UserStateRedisService;
import com.adrabazha.air.attack.alarm.system.telegram.processor.CommandProcessor;
import com.adrabazha.air.attack.alarm.system.telegram.wrapper.SendMessageWrapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static java.lang.String.format;

@Component
public class SubmitAdministrationRequestHandler extends BaseTelegramInputHandler<SubmitAdministrationRequestHandler> {

    protected SubmitAdministrationRequestHandler(
            List<CommandProcessor<SubmitAdministrationRequestHandler>> commandProcessors,
            UserService userService,
            UserStateRedisService userStateRedisService,
            ApplicationEventPublisher eventPublisher) {
        super(commandProcessors, userService, userStateRedisService, eventPublisher);
    }

    @Override
    protected SendMessageWrapper handleDefault(Update update) {
        SendMessageWrapper wrapper = new SendMessageWrapper();
        wrapper.setChatId(update.getMessage().getChatId().toString());

        User persistedUser = userService.findOrCreate(update);

        Long userId = persistedUser.getId();
        if (persistedUser.getAdministrationRequestSent()) {
            wrapper.setText(format("Ваша заявка розглядається :face_with_monocle:%nНомер вашої заявки - *#%d*", userId));
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
        return "Хочете допомогти країні і повідомляти людей про небезпеку?\n\n" +
                "Гаразд! Давайте спробуєм, залиште повідомлення з метою вашого запиту," +
                " та вкажіть скільки часу ви готові приділяти цій справі.";
    }

    @Override
    public Class<? extends TelegramInputHandler> getPredecessor() {
        return MainMenuHandler.class;
    }
}
