package com.adrabazha.air.attack.alarm.system.service;

import com.adrabazha.air.attack.alarm.system.dto.UserSubscription;
import com.adrabazha.air.attack.alarm.system.model.domain.District;
import com.adrabazha.air.attack.alarm.system.model.domain.User;
import com.adrabazha.air.attack.alarm.system.model.domain.redis.UserState;
import com.adrabazha.air.attack.alarm.system.repository.UserRepository;
import com.adrabazha.air.attack.alarm.system.repository.UserStateRedisRepository;
import com.adrabazha.air.attack.alarm.system.telegram.handler.MainMenuHandler;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserRedisServiceImpl implements UserService, UserStateRedisService, SubscriptionService {

    private final UserStateRedisRepository userStateRedisRepository;
    private final UserRepository userRepository;

    public UserRedisServiceImpl(UserStateRedisRepository userStateRedisRepository, UserRepository userRepository) {
        this.userStateRedisRepository = userStateRedisRepository;
        this.userRepository = userRepository;
    }

    @Override
    public User findOrCreate(Update update) {
        Optional<User> persistedUser = userRepository.findByTelegramUserId(update.getMessage().getFrom().getId().toString());
        if (persistedUser.isPresent()) {
            return persistedUser.get();
        }

        org.telegram.telegrambots.meta.api.objects.User sender = update.getMessage().getFrom();

        User user = User.builder()
                .telegramUsername(sender.getUserName())
                .telegramUserId(sender.getId().toString())
                .chatId(update.getMessage().getChatId().toString())
                .isAdmin(false)
                .administrationRequestSent(false)
                .requestMessage("")
                .build();

        return userRepository.save(user);
    }

    @Override
    public User getUserByTelegramId(String telegramUserId) {
        return userRepository.findByTelegramUserId(telegramUserId)
                .orElseThrow(() -> new RuntimeException("User was not found"));
    }

    @Override
    public UserState getOrCreateState(String userId) {
        Optional<UserState> persistedState = userStateRedisRepository.findById(userId);

        return persistedState.orElseGet(() -> userStateRedisRepository.save(
                UserState.builder()
                        .userId(userId)
                        .currentHandler(MainMenuHandler.class.getName())
                        .build()));
    }

    @Override
    public UserState updateState(UserState userState) {
        userStateRedisRepository.delete(userState);
        return userStateRedisRepository.save(userState);
    }

    @Override
    public Boolean isAdministrator(Update update) {
        return findOrCreate(update).getIsAdmin();
    }

    @Override
    public void removeSubscriber(User user, District district) {
        userRepository.removeSubscriber(user.getId(), district.getId());
    }

    @Override
    public void addSubscriber(User user, District district) {
        userRepository.addSubscriber(user.getId(), district.getId());
    }

    @Override
    public Boolean isUserSubscribed(User user, District district) {
        return userRepository.isUserSubscribed(user.getId(), district.getId());
    }

    @Override
    public List<User> findSubscribersByDistrict(District district) {
        return userRepository.findSubscribersByDistrict(district.getId());
    }

    @Override
    public User updateUser(User persistedUser) {
        return userRepository.save(persistedUser);
    }

    @Override
    public List<UserSubscription> getUserSubscriptions(Update update) {
        User user = findOrCreate(update);
        List<Map<String, Object>> rawUserSubscriptionList = userRepository.findUserSubscription(user.getId());

        return rawUserSubscriptionList.stream()
                .map(this::convertUserSubscription)
                .collect(Collectors.toList());
    }

    private UserSubscription convertUserSubscription(Map<String, Object> rawObject) {
        Boolean isSubscribed = (Boolean) rawObject.get("is_subscribed");
        String districtCode = rawObject.get("district_code").toString();
        String districtName = rawObject.get("district_name").toString();

        return UserSubscription.of(districtCode, districtName, isSubscribed);
    }
}
