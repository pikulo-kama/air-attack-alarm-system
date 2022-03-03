package com.adrabazha.air.attack.alarm.system.repository;

import com.adrabazha.air.attack.alarm.system.model.domain.redis.DistrictState;
import org.springframework.data.repository.CrudRepository;

public interface DistrictStateRepository extends CrudRepository<DistrictState, String> {
}
