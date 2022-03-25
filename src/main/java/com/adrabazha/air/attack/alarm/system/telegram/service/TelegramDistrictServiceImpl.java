package com.adrabazha.air.attack.alarm.system.telegram.service;

import com.adrabazha.air.attack.alarm.system.model.domain.redis.DistrictState;
import com.adrabazha.air.attack.alarm.system.service.DistrictStateRedisService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TelegramDistrictServiceImpl implements TelegramDistrictService {

    private final DistrictStateRedisService districtStateRedisService;

    public TelegramDistrictServiceImpl(DistrictStateRedisService districtStateRedisService) {
        this.districtStateRedisService = districtStateRedisService;
    }

    @Override
    public List<DistrictState> getDistrictStatesByInput(String input) {
        return districtStateRedisService.findAllStates().stream()
                .filter(district -> partiallyMatches(input, district))
                .collect(Collectors.toList());
    }

    private Boolean partiallyMatches(String input, DistrictState district) {
        return district.getDistrictName().toLowerCase().contains(input.toLowerCase());
    }
}
