package com.adrabazha.air.attack.alarm.system.service;

import com.adrabazha.air.attack.alarm.system.dto.DistrictMapStateObject;
import com.adrabazha.air.attack.alarm.system.model.domain.District;

import java.util.List;

public interface DistrictService {

    List<District> findAll();

    List<DistrictMapStateObject> getGeoData();
}
