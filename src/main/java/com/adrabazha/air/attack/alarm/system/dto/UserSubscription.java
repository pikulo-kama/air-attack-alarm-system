package com.adrabazha.air.attack.alarm.system.dto;

import com.adrabazha.air.attack.alarm.system.model.SubscriptionState;
import com.adrabazha.air.attack.alarm.system.telegram.callback.SubscriptionChangeCallbackHandler;
import com.adrabazha.air.attack.alarm.system.utils.CallbackDataHelper;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.adrabazha.air.attack.alarm.system.model.SubscriptionState.DISABLED;
import static com.adrabazha.air.attack.alarm.system.model.SubscriptionState.ENABLED;

@NoArgsConstructor
@Data
public class UserSubscription {

    private String districtCode;

    private String districtName;

    private SubscriptionState subscriptionState;

    private UserSubscription(String districtCode, String districtName, Boolean isSubscribed) {
        this.districtCode = districtCode;
        this.districtName = districtName;
        this.subscriptionState = isSubscribed ? ENABLED : DISABLED;
    }

    public static UserSubscription of(String districtCode, String districtName, Boolean isSubscribed) {
        return new UserSubscription(districtCode, districtName, isSubscribed);
    }

    public String buildExtendedSubscriptionMessage() {
        return String.format(subscriptionState.getSubscriptionMessage(), districtName);
    }

    public String buildSubscriptionMessage() {
        return String.format("%s %s", subscriptionState.getEmoji(), districtName);
    }

    public String buildCallbackData() {
        return CallbackDataHelper.payloadBuilder()
                .setCallbackQueryHandler(SubscriptionChangeCallbackHandler.class)
                .simpleDataUnit(districtCode)
                .build();
    }
}
