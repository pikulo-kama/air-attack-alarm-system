package com.adrabazha.air.attack.alarm.system.telegram.handler;

import com.adrabazha.air.attack.alarm.system.service.UserService;
import com.adrabazha.air.attack.alarm.system.telegram.processor.CommandProcessor;
import com.adrabazha.air.attack.alarm.system.telegram.service.TelegramDistrictService;
import com.adrabazha.air.attack.alarm.system.telegram.wrapper.SendMessageWrapper;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
public class DistrictSearchInputHandler extends BaseTelegramInputHandler<DistrictSearchInputHandler> {

    private final TelegramDistrictService telegramDistrictService;

    public DistrictSearchInputHandler(
            List<CommandProcessor<DistrictSearchInputHandler>> processors,
            UserService userService, TelegramDistrictService telegramDistrictService) {
        super(processors, userService);
        this.telegramDistrictService = telegramDistrictService;
    }

    @Override
    protected String defaultMessage() {
        return "Введіть назву області або її частину щоб переглянути" +
                " її поточний стан \uD83C\uDDFA\uD83C\uDDE6 \uD83D\uDE4C";
    }

    @Override
    protected SendMessageWrapper handleDefault(Update update) {
        return telegramDistrictService.buildDistrictStateResponse(update);
    }

    @Override
    public Class<? extends TelegramInputHandler> getPredecessor() {
        return MainMenuHandler.class;
    }
}
