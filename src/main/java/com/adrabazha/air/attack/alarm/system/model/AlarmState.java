package com.adrabazha.air.attack.alarm.system.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AlarmState {
    OFF(
            ":white_circle:",
            ":ua: :dove: Наразі в області - %s все тихо...",
            "Ввімкнути тривогу :loud_sound:"
    ),
    ON(
            ":red_circle:",
            ":warning: :boom: Увага!!! Повітряна тривога на території області - %s",
            "Вимкнути тривогу :mute:"
    );

    private String emoji;
    private String statusMessagePattern;
    private String alarmButtonContent;
}
