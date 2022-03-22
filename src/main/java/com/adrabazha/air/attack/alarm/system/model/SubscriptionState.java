package com.adrabazha.air.attack.alarm.system.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum SubscriptionState {
    ENABLED(
            "Ви підписанні на оповіщення про повітряну тривогу в області - %s",
            "Відмінити підписку",
            "\u2611\uFE0F"
    ),
    DISABLED(
            "Ви ще не підписанні на оповіщення про повітряну тривогу в області - %s",
            "Підписатись на розсилку",
            "\u274E"
    );

    @Getter
    private String subscriptionMessage;

    @Getter
    private String buttonName;

    @Getter
    private String emoji;
}
