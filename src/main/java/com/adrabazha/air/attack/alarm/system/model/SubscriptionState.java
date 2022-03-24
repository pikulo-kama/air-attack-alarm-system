package com.adrabazha.air.attack.alarm.system.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum SubscriptionState {
    ENABLED(
            ":white_check_mark: Ви підписанні на оповіщення про повітряну тривогу в області - %s",
            "Відмінити підписку :x:",
            ":ballot_box_with_check:"
    ),
    DISABLED(
            ":x: Ви ще не підписанні на оповіщення про повітряну тривогу в області - %s",
            "Підписатись на розсилку :white_check_mark:",
            ":negative_squared_cross_mark:"
    );

    @Getter
    private String subscriptionMessage;

    @Getter
    private String buttonName;

    @Getter
    private String emoji;
}
