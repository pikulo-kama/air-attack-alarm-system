package com.adrabazha.air.attack.alarm.system.service;

import com.adrabazha.air.attack.alarm.system.model.AlarmState;
import com.adrabazha.air.attack.alarm.system.model.domain.AlarmRecord;
import com.adrabazha.air.attack.alarm.system.model.domain.District;
import com.adrabazha.air.attack.alarm.system.model.domain.User;
import com.adrabazha.air.attack.alarm.system.repository.AlarmHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AlarmHistoryServiceImpl implements AlarmHistoryService {

    private final AlarmHistoryRepository alarmHistoryRepository;

    public AlarmHistoryServiceImpl(AlarmHistoryRepository alarmHistoryRepository) {
        this.alarmHistoryRepository = alarmHistoryRepository;
    }

    @Override
    public AlarmRecord updateHistory(User user, District district, AlarmState alarmState) {
        AlarmRecord alarmRecord = AlarmRecord.builder()
                .userId(user.getId())
                .districtId(district.getId())
                .alarmState(alarmState.name())
                .performedAt(new Date())
                .build();

        return alarmHistoryRepository.save(alarmRecord);
    }
}
