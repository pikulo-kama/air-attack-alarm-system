package com.adrabazha.air.attack.alarm.system.service;

import com.adrabazha.air.attack.alarm.system.model.domain.User;
import com.adrabazha.air.attack.alarm.system.model.domain.redis.UserState;

import java.util.Optional;

public interface UserService {

    Optional<User> findByTelegramUserId(String userId);

    UserState getOrCreateState(String userId);

    UserState updateState(UserState userState);

    Boolean isAdministrator(String userId);

    User createUser(User user);
}
