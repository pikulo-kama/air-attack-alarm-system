package com.adrabazha.air.attack.alarm.system.repository;

import com.adrabazha.air.attack.alarm.system.model.domain.AlarmRecord;
import org.springframework.data.repository.CrudRepository;

public interface AlarmHistoryRepository extends CrudRepository<AlarmRecord, Long> {
}
