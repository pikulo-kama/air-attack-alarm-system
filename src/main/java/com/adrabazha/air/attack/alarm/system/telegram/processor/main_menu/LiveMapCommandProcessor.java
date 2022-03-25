package com.adrabazha.air.attack.alarm.system.telegram.processor.main_menu;

import com.adrabazha.air.attack.alarm.system.telegram.custom.CustomInlineKeyboardButton;
import com.adrabazha.air.attack.alarm.system.telegram.handler.MainMenuHandler;
import com.adrabazha.air.attack.alarm.system.telegram.processor.CommandProcessor;
import com.adrabazha.air.attack.alarm.system.telegram.wrapper.SendMessageWrapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class LiveMapCommandProcessor implements CommandProcessor<MainMenuHandler> {

    @Value("${frontend.url}")
    private String frontendUrl;

    @Override
    public SendMessageWrapper process(Update update) {
        SendMessageWrapper wrapper = new SendMessageWrapper();
        wrapper.setChatId(update.getMessage().getChatId().toString());

        String message = ":world_map: Даний сервіс дозволяє вам стежити за ситуацією в Україні в режимі LIVE.\n\n" +
                "Заповнивши поле *\"Обласний центр\"* ви будете додатково отримувати сповіщення в формі :loudspeaker:," +
                " як тільки в вашій області буде попередження про повітряну тривогу. :face_with_cowboy_hat: :ua:";
        wrapper.setText(message);

        CustomInlineKeyboardButton openMapButton = new CustomInlineKeyboardButton();
        openMapButton.setText("Відкрити мапу :compass:");
        openMapButton.setUrl(frontendUrl);
        wrapper.addInlineButton(openMapButton);

        return wrapper;
    }

    @Override
    public String getCommandName() {
        return ":compass: Live-мапа України :compass:";
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
