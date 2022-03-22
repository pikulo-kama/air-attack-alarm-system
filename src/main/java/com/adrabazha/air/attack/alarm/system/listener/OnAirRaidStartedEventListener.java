package com.adrabazha.air.attack.alarm.system.listener;

import com.adrabazha.air.attack.alarm.system.event.AirRaidStartedEvent;
import com.adrabazha.air.attack.alarm.system.service.DistrictService;
import com.adrabazha.air.attack.alarm.system.service.SubscriptionService;
import com.adrabazha.air.attack.alarm.system.telegram.AirRaidBot;
import org.springframework.stereotype.Component;

@Component
public class OnAirRaidStartedEventListener extends AirRaidApplicationListener<AirRaidStartedEvent> {

    public OnAirRaidStartedEventListener(AirRaidBot bot,
                                       SubscriptionService subscriptionService,
                                       DistrictService districtService) {
        super(bot, subscriptionService, districtService, (district, user) ->
                String.format("Увага!!! Повітряна тривога на території області - %s", district.getName()));
    }
}