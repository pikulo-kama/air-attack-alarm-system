package com.adrabazha.air.attack.alarm.system.service;

import com.adrabazha.air.attack.alarm.system.model.AlarmState;
import com.adrabazha.air.attack.alarm.system.model.domain.AlarmRecord;
import com.adrabazha.air.attack.alarm.system.model.domain.District;
import com.adrabazha.air.attack.alarm.system.model.domain.User;

public interface AlarmHistoryService {

    AlarmRecord updateHistory(User user, District district, AlarmState alarmState);
}
