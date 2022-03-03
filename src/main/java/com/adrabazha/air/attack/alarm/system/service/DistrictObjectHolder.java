package com.adrabazha.air.attack.alarm.system.service;

import com.adrabazha.air.attack.alarm.system.dto.DistrictMapStateObject;
import com.adrabazha.air.attack.alarm.system.dto.DistrictStateObject;
import com.adrabazha.air.attack.alarm.system.model.domain.District;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.adrabazha.air.attack.alarm.system.model.AlarmState.OFF;
import static com.adrabazha.air.attack.alarm.system.model.AlarmState.ON;

@Scope("singleton")
@Component
@NoArgsConstructor
public class DistrictObjectHolder {

    @Getter
    private List<DistrictMapStateObject> mapObjects = new ArrayList<>();

    public void initialize(List<District> districtList) {
        this.mapObjects = new ArrayList<>();
        this.mapObjects.addAll(
                districtList.stream()
                .map(district -> new DistrictMapStateObject(district.getName(), district.getCode(), OFF, district.getGeoJson()))
                .collect(Collectors.toList()));
    }

    public void toggle(String key) {
        mapObjects
                .stream()
                .filter(district -> Objects.equals(district.getDistrictCode(), key))
                .findFirst()
                .ifPresent(district -> district.setAlarmState( district.getAlarmState().equals(ON) ? OFF : ON));
    }

    public List<DistrictStateObject> toState() {
        return mapObjects
                .stream()
                .map(obj -> new DistrictStateObject(obj.getDistrictName(), obj.getDistrictCode(), obj.getAlarmState()))
                .collect(Collectors.toList());
    }
}
