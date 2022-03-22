package com.adrabazha.air.attack.alarm.system.telegram.service;

import com.adrabazha.air.attack.alarm.system.model.domain.redis.DistrictState;

import java.util.List;

public interface TelegramDistrictService {
    List<DistrictState> getDistrictStatesByInput(String input);
}
