package com.adrabazha.air.attack.alarm.system.controller.rest;

import com.adrabazha.air.attack.alarm.system.dto.DistrictMapStateObject;
import com.adrabazha.air.attack.alarm.system.model.domain.redis.DistrictState;
import com.adrabazha.air.attack.alarm.system.service.DistrictService;
import com.adrabazha.air.attack.alarm.system.service.DistrictStateService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/districts")
public class DistrictRestController {

    private final DistrictStateService districtStateService;
    private final DistrictService districtService;

    public DistrictRestController(DistrictStateService districtStateService,
                                  DistrictService districtService) {
        this.districtStateService = districtStateService;
        this.districtService = districtService;
    }

    @CrossOrigin
    @GetMapping("/geo")
    public List<DistrictMapStateObject> getDistrictMapObjects() {
        return districtService.getGeoData();
    }

    @CrossOrigin
    @GetMapping("/states")
    public List<DistrictState> getDistrictStates() {
        return districtStateService.findAllStates();
    }
}
