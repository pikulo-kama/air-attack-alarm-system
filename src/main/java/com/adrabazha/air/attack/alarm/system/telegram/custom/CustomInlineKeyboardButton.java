package com.adrabazha.air.attack.alarm.system.telegram.custom;

import com.vdurmont.emoji.EmojiParser;
import lombok.NonNull;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class CustomInlineKeyboardButton extends InlineKeyboardButton {

    @Override
    public void setText(@NonNull String text) {
        text = EmojiParser.parseToUnicode(text);
        super.setText(text);
    }

//    @Override
//    public @NonNull String getText() {
//        return EmojiParser.parseToAliases(super.getText());
//    }
}
