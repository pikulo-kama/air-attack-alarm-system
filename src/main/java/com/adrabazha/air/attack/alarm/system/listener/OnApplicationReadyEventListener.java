package com.adrabazha.air.attack.alarm.system.listener;

import com.adrabazha.air.attack.alarm.system.service.DistrictStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class OnApplicationReadyEventListener
        implements ApplicationListener<ApplicationReadyEvent> {

    private final DistrictStateService districtStateService;

    @Autowired
    public OnApplicationReadyEventListener(DistrictStateService districtStateService) {
        this.districtStateService = districtStateService;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        districtStateService.initialize();
    }
}
