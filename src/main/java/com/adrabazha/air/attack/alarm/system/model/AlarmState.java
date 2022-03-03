package com.adrabazha.air.attack.alarm.system.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static java.lang.String.format;

@AllArgsConstructor
@Getter
public enum AlarmState {
    OFF(
            "\uD83D\uDFE2",
            "\uD83C\uDDFA\uD83C\uDDE6 \uD83D\uDD4A Наразі в області - %s все тихо...",
            "Ввімкнути тривогу \uD83D\uDD0A"
    ),
    ON(
            "\uD83D\uDFE7",
            "\u26A0 \uD83D\uDCA3 Увага!!! Повітряна тривога на території області - %s",
            "Вимкнути тривогу \uD83D\uDD07"
    );

    private String emoji;
    private String statusMessagePattern;
    private String alarmButtonContent;
}
