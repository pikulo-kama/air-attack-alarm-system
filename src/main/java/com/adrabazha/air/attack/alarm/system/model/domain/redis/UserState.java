package com.adrabazha.air.attack.alarm.system.model.domain.redis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@RedisHash("UserState")
public class UserState {

    @Id
    private String userId;

    private String currentHandler;

    private String selectedDistrict;
}
