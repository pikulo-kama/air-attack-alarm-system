package com.adrabazha.air.attack.alarm.system.telegram.callback;

import com.adrabazha.air.attack.alarm.system.telegram.wrapper.SendMessageWrapper;
import com.adrabazha.air.attack.alarm.system.utils.CallbackDataHelper;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public abstract class BaseCallbackHandler implements CallbackHandler {

    @Override
    public SendMessageWrapper handle(Update update) {
        List<String> data = CallbackDataHelper.parseData(update.getCallbackQuery().getData());
        return handle(update.getCallbackQuery(), data);
    }

    abstract SendMessageWrapper handle(CallbackQuery callbackQuery, List<String> data);
}
