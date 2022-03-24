package com.adrabazha.air.attack.alarm.system.telegram.handler;

import com.adrabazha.air.attack.alarm.system.service.UserService;
import com.adrabazha.air.attack.alarm.system.service.UserStateService;
import com.adrabazha.air.attack.alarm.system.telegram.processor.CommandProcessor;
import com.adrabazha.air.attack.alarm.system.telegram.processor.main_menu.AdminRequestCommandProcessor;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

@Component
public class MainMenuHandler extends BaseTelegramInputHandler<MainMenuHandler> {

    public MainMenuHandler(List<CommandProcessor<MainMenuHandler>> processors,
                           UserService userService,
                           UserStateService userStateService) {
        super(processors, userService, userStateService);
    }

    @Override
    public String defaultMessage() {
        return "Привіт :wave:, я можу допомогти тобі отримувати актуальну інформацію про " +
                "стан сирен в різних частинах України :face_with_monocle:.\n\n" +
                "Разом ми переможемо ворога :couple_with_heart:\n" +
                "Разом ми - сила :muscle:\n" +
                "Разом ми - Україна :ua:\n";
    }

    @Override
    public List<KeyboardRow> getReplyKeyboard(Update update) {
        List<KeyboardRow> replyKeyboard = super.getReplyKeyboard(update);

        String adminRequestCommand = EmojiParser.parseToUnicode(AdminRequestCommandProcessor.COMMAND_NAME);
        if (userService.isAdministrator(update)) {
            replyKeyboard.removeIf(keyboardRow -> keyboardRow.contains(adminRequestCommand));
        }
        return replyKeyboard;
    }
}
