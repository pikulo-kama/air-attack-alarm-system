package com.adrabazha.air.attack.alarm.system.service;

import com.adrabazha.air.attack.alarm.system.dto.UserSubscription;
import com.adrabazha.air.attack.alarm.system.model.domain.District;
import com.adrabazha.air.attack.alarm.system.model.domain.User;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public interface SubscriptionService {

    void removeSubscriber(User user, District district);

    void addSubscriber(User user, District district);

    Boolean isUserSubscribed(User user, District district);

    List<User> findSubscribersByDistrict(District district);

    List<UserSubscription> getUserSubscriptions(Update update);
}
