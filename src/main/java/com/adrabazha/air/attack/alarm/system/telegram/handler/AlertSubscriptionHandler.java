package com.adrabazha.air.attack.alarm.system.telegram.handler;

import com.adrabazha.air.attack.alarm.system.dto.UserSubscription;
import com.adrabazha.air.attack.alarm.system.service.SubscriptionService;
import com.adrabazha.air.attack.alarm.system.service.UserService;
import com.adrabazha.air.attack.alarm.system.service.UserStateService;
import com.adrabazha.air.attack.alarm.system.telegram.custom.CustomInlineKeyboardButton;
import com.adrabazha.air.attack.alarm.system.telegram.processor.CommandProcessor;
import com.adrabazha.air.attack.alarm.system.telegram.wrapper.ConditionalSendMessageWrapperBuilder;
import com.adrabazha.air.attack.alarm.system.telegram.wrapper.SendMessageWrapper;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AlertSubscriptionHandler extends BaseTelegramInputHandler<AlertSubscriptionHandler> {

    private final SubscriptionService subscriptionService;

    public AlertSubscriptionHandler(List<CommandProcessor<AlertSubscriptionHandler>> commandProcessors,
                                    UserService userService,
                                    UserStateService userStateService,
                                    SubscriptionService subscriptionService) {
        super(commandProcessors, userService, userStateService);
        this.subscriptionService = subscriptionService;
    }

    @Override
    protected String defaultMessage() {
        return "Введіть назву області або її частину щоб переглянути" +
                " чи змінити оповіщення про тривогу :cop:";
    }

    @Override
    protected SendMessageWrapper handleDefault(Update update) {
        List<UserSubscription> filteredSubscriptions = subscriptionService.getUserSubscriptions(update)
                .stream()
                .filter(subscription -> partiallyMatches(update, subscription))
                .collect(Collectors.toList());

        return new ConditionalSendMessageWrapperBuilder<UserSubscription>()
                .chatId(update.getMessage().getChatId().toString())

                .notFoundResponseMessage(":thinking: За вашим запитом нічого не знайдено\nСпробуйте ще раз")

                .provideSingleResponseMessage(UserSubscription::buildExtendedSubscriptionMessage)
                .provideSingleResponseKeyboardButton(subscription -> {
                    CustomInlineKeyboardButton inlineKeyboardButton = new CustomInlineKeyboardButton();
                    inlineKeyboardButton.setText(subscription.getSubscriptionState().getButtonName());
                    inlineKeyboardButton.setCallbackData(subscription.buildCallbackData());

                    return inlineKeyboardButton;
                })

                .multiResponseMessageHeader(":thinking: Можливо ви мали на увазі щось з наступного..")
                .provideMultiResponseMessage(UserSubscription::buildSubscriptionMessage)

                .build(filteredSubscriptions);
    }

    private boolean partiallyMatches(Update update, UserSubscription subscription) {
        return subscription.getDistrictName().toLowerCase().contains(update.getMessage().getText().toLowerCase());
    }

    @Override
    public Class<? extends TelegramInputHandler> getPredecessor() {
        return MainMenuHandler.class;
    }
}
