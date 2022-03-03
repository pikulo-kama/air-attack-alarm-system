package com.adrabazha.air.attack.alarm.system.service;

import com.adrabazha.air.attack.alarm.system.model.domain.redis.DistrictState;

import java.util.List;

public interface DistrictStateService {

    void initialize();

    List<DistrictState> findAllStates();

    DistrictState getDistrictState(String districtCode);

    DistrictState updateDistrictState(DistrictState updatedState);
}
