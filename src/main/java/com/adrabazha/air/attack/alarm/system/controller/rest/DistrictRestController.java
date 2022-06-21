package com.adrabazha.air.attack.alarm.system.controller.rest;

import com.adrabazha.air.attack.alarm.system.model.domain.redis.DistrictState;
import com.adrabazha.air.attack.alarm.system.service.DistrictStateRedisService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/districts")
public class DistrictRestController {

    private final DistrictStateRedisService districtStateRedisService;

    public DistrictRestController(DistrictStateRedisService districtStateRedisService) {
        this.districtStateRedisService = districtStateRedisService;
    }

    @CrossOrigin
    @GetMapping("/states")
    public List<DistrictState> getDistrictStates() {
        return districtStateRedisService.findAllStates();
    }
}
