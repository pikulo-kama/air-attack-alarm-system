package com.adrabazha.air.attack.alarm.system.model.domain.redis;

import com.adrabazha.air.attack.alarm.system.model.AlarmState;
import com.adrabazha.air.attack.alarm.system.telegram.callback.AlarmStateChangeCallbackHandler;
import com.adrabazha.air.attack.alarm.system.utils.CallbackDataHelper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import static com.adrabazha.air.attack.alarm.system.model.AlarmState.*;

@RedisHash("DistrictState")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DistrictState {

    @Id
    private String districtCode;

    private String districtName;

    private AlarmState alarmState;

    public void toggleAlarm() {
        alarmState = alarmState.equals(OFF) ? ON : OFF;
    }

    public String buildCallbackData() {
        return CallbackDataHelper.payloadBuilder()
                .setCallbackQueryHandler(AlarmStateChangeCallbackHandler.class)
                .simpleDataUnit(districtCode)
                .build();
    }

    public String getStateMessage() {
        return String.format(alarmState.getStatusMessagePattern(), districtName);
    }
}
