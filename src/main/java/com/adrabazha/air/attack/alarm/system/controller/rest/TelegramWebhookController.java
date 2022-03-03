package com.adrabazha.air.attack.alarm.system.controller.rest;

import com.adrabazha.air.attack.alarm.system.telegram.AirRaidBot;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
public class TelegramWebhookController {

    private final AirRaidBot airRaidBot;

    public TelegramWebhookController(AirRaidBot airRaidBot) {
        this.airRaidBot = airRaidBot;
    }

    @PostMapping("/")
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return airRaidBot.onWebhookUpdateReceived(update);
    }
}
