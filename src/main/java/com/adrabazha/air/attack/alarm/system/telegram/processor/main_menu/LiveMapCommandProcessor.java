package com.adrabazha.air.attack.alarm.system.telegram.processor.main_menu;

import com.adrabazha.air.attack.alarm.system.telegram.handler.MainMenuHandler;
import com.adrabazha.air.attack.alarm.system.telegram.processor.CommandProcessor;
import com.adrabazha.air.attack.alarm.system.telegram.wrapper.SendMessageWrapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Component
public class LiveMapCommandProcessor implements CommandProcessor<MainMenuHandler> {

    @Value("${frontend.url}")
    private String frontendUrl;

    @Override
    public SendMessageWrapper process(Update update) {
        SendMessageWrapper wrapper = new SendMessageWrapper();
        wrapper.setChatId(update.getMessage().getChatId().toString());

        String message = "\uD83D\uDDFA\uFE0F Даний сервіс дозволяє вам стежити за ситуацією в Україні в режимі LIVE.\n\n" +
                "Заповнивши поле *\"Обласний центр\"* ви будете додатково отримувати сповіщення в формі \uD83D\uDCE2," +
                " як тільки в вашій області буде попередження про повітряну тривогу. \uD83E\uDD20 \uD83C\uDDFA\uD83C\uDDE6";
        wrapper.setText(message);

        InlineKeyboardButton openMapButton = new InlineKeyboardButton("Відкрити мапу \uD83E\uDDED");
        openMapButton.setUrl(frontendUrl);
        wrapper.addInlineButton(openMapButton);

        return wrapper;
    }

    @Override
    public String getCommandName() {
        return "\uD83E\uDDED Live-мапа України \uD83E\uDDED";
    }

    @Override
    public Boolean isAccessRestricted() {
        return false;
    }

    @Override
    public Integer getPosition() {
        return 4;
    }
}
