package com.adrabazha.air.attack.alarm.system.service;

import com.adrabazha.air.attack.alarm.system.model.domain.redis.UserState;

public interface UserStateRedisService {

    UserState getOrCreateState(String userId);

    UserState updateState(UserState userState);
}
