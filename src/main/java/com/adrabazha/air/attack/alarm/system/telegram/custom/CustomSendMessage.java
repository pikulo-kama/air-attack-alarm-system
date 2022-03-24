package com.adrabazha.air.attack.alarm.system.telegram.custom;

import com.vdurmont.emoji.EmojiParser;
import lombok.NonNull;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class CustomSendMessage extends SendMessage {

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
