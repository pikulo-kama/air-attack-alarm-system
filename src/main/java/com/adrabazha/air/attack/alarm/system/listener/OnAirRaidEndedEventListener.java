package com.adrabazha.air.attack.alarm.system.listener;

import com.adrabazha.air.attack.alarm.system.event.AirRaidEndedEvent;
import com.adrabazha.air.attack.alarm.system.service.DistrictService;
import com.adrabazha.air.attack.alarm.system.service.SubscriptionService;
import com.adrabazha.air.attack.alarm.system.telegram.AirRaidBot;
import org.springframework.stereotype.Component;

@Component
public class OnAirRaidEndedEventListener extends AirRaidApplicationListener<AirRaidEndedEvent> {

    public OnAirRaidEndedEventListener(AirRaidBot bot,
                                       SubscriptionService subscriptionService,
                                       DistrictService districtService) {
        super(bot, subscriptionService, districtService, (district, user) ->
                String.format(":warning: Увага!!! Відбій повітряної тривоги на території області - *%s*%n%n" +
                        "Можете повертатись до звичного життя :relieved:", district.getName()));
    }
}
