package com.adrabazha.air.attack.alarm.system.telegram.callback;

import com.adrabazha.air.attack.alarm.system.dto.UserSubscription;
import com.adrabazha.air.attack.alarm.system.model.domain.District;
import com.adrabazha.air.attack.alarm.system.model.domain.User;
import com.adrabazha.air.attack.alarm.system.service.DistrictService;
import com.adrabazha.air.attack.alarm.system.service.SubscriptionService;
import com.adrabazha.air.attack.alarm.system.service.UserService;
import com.adrabazha.air.attack.alarm.system.telegram.wrapper.SendMessageWrapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

@Component
public class SubscriptionChangeCallbackHandler extends BaseCallbackHandler {

    private final SubscriptionService subscriptionService;
    private final UserService userService;
    private final DistrictService districtService;

    public SubscriptionChangeCallbackHandler(SubscriptionService subscriptionService,
                                             UserService userService,
                                             DistrictService districtService) {
        this.subscriptionService = subscriptionService;
        this.userService = userService;
        this.districtService = districtService;
    }

    @Override
    @Transactional
    SendMessageWrapper handle(CallbackQuery callbackQuery, List<String> data) {
        String districtCode = data.get(0);
        SendMessageWrapper wrapper = new SendMessageWrapper();

        wrapper.setChatId(callbackQuery.getMessage().getChatId().toString());
        wrapper.setMessageId(callbackQuery.getMessage().getMessageId());
        wrapper.replyMarkupEdited(true);

        User user = userService.getUserByTelegramId(callbackQuery.getFrom().getId().toString());
        District district = districtService.findByCode(districtCode);
        Boolean isSubscribed = subscriptionService.isUserSubscribed(user, district);

        if (isSubscribed) {
            subscriptionService.removeSubscriber(user, district);
            isSubscribed = false;
        } else {
            subscriptionService.addSubscriber(user, district);
            isSubscribed = true;
        }

        UserSubscription subscription = UserSubscription.of(district.getCode(), district.getName(), isSubscribed);

        wrapper.setText(subscription.buildExtendedSubscriptionMessage());
        InlineKeyboardButton button = new InlineKeyboardButton(subscription.getSubscriptionState().getButtonName());
        button.setCallbackData(subscription.buildCallbackData());
        wrapper.addInlineButton(button);

        return wrapper;
    }
}
