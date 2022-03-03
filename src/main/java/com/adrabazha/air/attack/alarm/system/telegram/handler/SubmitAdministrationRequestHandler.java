package com.adrabazha.air.attack.alarm.system.telegram.handler;

import com.adrabazha.air.attack.alarm.system.model.domain.User;
import com.adrabazha.air.attack.alarm.system.service.UserService;
import com.adrabazha.air.attack.alarm.system.telegram.processor.CommandProcessor;
import com.adrabazha.air.attack.alarm.system.telegram.wrapper.SendMessageWrapper;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Component
public class SubmitAdministrationRequestHandler extends BaseTelegramInputHandler<SubmitAdministrationRequestHandler> {

    protected SubmitAdministrationRequestHandler(
            List<CommandProcessor<SubmitAdministrationRequestHandler>> commandProcessors,
            UserService userService) {
        super(commandProcessors, userService);
    }

    @Override
    protected SendMessageWrapper handleDefault(Update update) {
        SendMessageWrapper wrapper = new SendMessageWrapper();
        wrapper.setChatId(update.getMessage().getChatId().toString());

        String telegramUserId = update.getMessage().getFrom().getId().toString();
        Optional<User> persistedUser = userService.findByTelegramUserId(telegramUserId);

        if (persistedUser.isPresent()) {
            Long userId = persistedUser.get().getId();
            wrapper.setText(format("Ваша заявка розглядається \uD83E\uDDD0%nНомер вашої заявки - *#%d*", userId));
        } else {
            Long userId = saveUser(update);
            wrapper.setText(format("Спасибі! Ваш запит прийнято, ось номер вашої заявки - *#%d*", userId));
        }
        return wrapper;
    }

    private Long saveUser(Update update) {
        User user = User.builder()
                .telegramUserId(update.getMessage().getFrom().getId().toString())
                .telegramUsername(update.getMessage().getFrom().getUserName())
                .requestMessage(update.getMessage().getText())
                .isAdmin(false)
                .build();

        User persistedUser = userService.createUser(user);
        return persistedUser.getId();
    }

    @Override
    protected String defaultMessage() {
        return "";
    }

    @Override
    public Class<? extends TelegramInputHandler> getPredecessor() {
        return MainMenuHandler.class;
    }
}
