package com.adrabazha.air.attack.alarm.system.repository;


import com.adrabazha.air.attack.alarm.system.model.domain.redis.UserState;
import org.springframework.data.repository.CrudRepository;

public interface UserStateRedisRepository extends CrudRepository<UserState, String> {
}
