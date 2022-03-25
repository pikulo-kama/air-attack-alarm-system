package com.adrabazha.air.attack.alarm.system.listener;

import com.adrabazha.air.attack.alarm.system.event.AirRaidEvent;
import com.adrabazha.air.attack.alarm.system.model.domain.District;
import com.adrabazha.air.attack.alarm.system.model.domain.User;
import com.adrabazha.air.attack.alarm.system.service.DistrictService;
import com.adrabazha.air.attack.alarm.system.service.SubscriptionService;
import com.adrabazha.air.attack.alarm.system.telegram.AirRaidBot;
import com.adrabazha.air.attack.alarm.system.telegram.custom.ExecuteAsyncMessageCallback;
import com.adrabazha.air.attack.alarm.system.telegram.wrapper.SendMessageWrapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import java.util.List;
import java.util.function.BiFunction;

@Slf4j
abstract class AirRaidApplicationListener<T extends ApplicationEvent & AirRaidEvent> implements ApplicationListener<T> {

    private final AirRaidBot bot;
    private final SubscriptionService subscriptionService;
    private final DistrictService districtService;
    private final BiFunction<District, User, String> messageFunction;

    protected AirRaidApplicationListener(AirRaidBot bot,
                                         SubscriptionService subscriptionService,
                                         DistrictService districtService,
                                         BiFunction<District, User, String> messageFunction) {
        this.bot = bot;
        this.subscriptionService = subscriptionService;
        this.districtService = districtService;
        this.messageFunction = messageFunction;
    }

    public void onApplicationEvent(T event) {
        District district = districtService.findByCode(event.getDistrictCode());
        List<User> subscribers = subscriptionService.findSubscribersByDistrict(district);
        subscribers.forEach(subscriber -> sendAlertMessage(district, subscriber));
    }

    @SneakyThrows
    private void sendAlertMessage(District district, User subscriber) {
        SendMessageWrapper wrapper = new SendMessageWrapper();
        wrapper.setChatId(subscriber.getChatId());
        wrapper.setText(messageFunction.apply(district, subscriber));

        bot.executeAsync(wrapper.build());
    }
}
