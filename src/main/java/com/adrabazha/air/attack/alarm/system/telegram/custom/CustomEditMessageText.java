package com.adrabazha.air.attack.alarm.system.telegram.custom;

import com.vdurmont.emoji.EmojiParser;
import lombok.NonNull;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

public class CustomEditMessageText extends EditMessageText {

    @Override
    public void setText(@NonNull String text) {
        text = EmojiParser.parseToUnicode(text);
        super.setText(text);
    }
}
