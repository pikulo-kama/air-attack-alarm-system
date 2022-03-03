package com.adrabazha.air.attack.alarm.system.telegram.callback;

import com.adrabazha.air.attack.alarm.system.telegram.wrapper.SendMessageWrapper;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface CallbackQueryHandler {

    SendMessageWrapper handle(Update update);
}
