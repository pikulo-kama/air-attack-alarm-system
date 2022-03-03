package com.adrabazha.air.attack.alarm.system.dto;

import com.adrabazha.air.attack.alarm.system.model.AlarmState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DistrictStateObject {

    private String districtName;

    private String districtCode;

    private AlarmState alarmState;
}
