package com.adrabazha.air.attack.alarm.system.listener;

import com.adrabazha.air.attack.alarm.system.service.DistrictStateRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class OnApplicationReadyEventListener
        implements ApplicationListener<ApplicationReadyEvent> {

    private final DistrictStateRedisService districtStateRedisService;

    @Autowired
    public OnApplicationReadyEventListener(DistrictStateRedisService districtStateRedisService) {
        this.districtStateRedisService = districtStateRedisService;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        districtStateRedisService.initialize();
    }
}
