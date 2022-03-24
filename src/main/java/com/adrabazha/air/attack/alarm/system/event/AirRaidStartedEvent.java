package com.adrabazha.air.attack.alarm.system.event;

import org.springframework.context.ApplicationEvent;

public class AirRaidStartedEvent extends ApplicationEvent implements AirRaidEvent {

    private final String districtCode;

    public AirRaidStartedEvent(Object source, String districtCode) {
        super(source);
        this.districtCode = districtCode;
    }

    @Override
    public String getDistrictCode() {
        return districtCode;
    }
}
