package com.adrabazha.air.attack.alarm.system.service;

import com.adrabazha.air.attack.alarm.system.model.domain.redis.UserState;

public interface UserStateService {

    UserState getOrCreateState(String userId);

    UserState updateState(UserState userState);
}
