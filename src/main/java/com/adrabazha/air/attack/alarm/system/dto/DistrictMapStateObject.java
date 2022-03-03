package com.adrabazha.air.attack.alarm.system.dto;

import com.adrabazha.air.attack.alarm.system.model.AlarmState;
import com.adrabazha.air.attack.alarm.system.model.domain.District;
import com.adrabazha.air.attack.alarm.system.model.domain.redis.DistrictState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DistrictMapStateObject {

    private String districtName;

    private String districtCode;

    private AlarmState alarmState;

    private String geoJson;

    public static DistrictMapStateObject fromDistrictAndState(District district, DistrictState state) {
        return new DistrictMapStateObject(
                district.getName(),
                district.getCode(),
                state.getAlarmState(),
                district.getGeoJson());
    }
}
