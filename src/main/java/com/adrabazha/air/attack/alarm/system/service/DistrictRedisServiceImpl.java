package com.adrabazha.air.attack.alarm.system.service;

import com.adrabazha.air.attack.alarm.system.dto.DistrictMapStateObject;
import com.adrabazha.air.attack.alarm.system.model.AlarmState;
import com.adrabazha.air.attack.alarm.system.model.domain.District;
import com.adrabazha.air.attack.alarm.system.model.domain.redis.DistrictState;
import com.adrabazha.air.attack.alarm.system.repository.DistrictRepository;
import com.adrabazha.air.attack.alarm.system.repository.DistrictStateRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
public class DistrictRedisServiceImpl implements DistrictService, DistrictStateRedisService {

    private final DistrictRepository districtRepository;
    private final DistrictStateRedisRepository districtStateRedisRepository;

    @Autowired
    public DistrictRedisServiceImpl(DistrictRepository districtRepository,
                                    DistrictStateRedisRepository districtStateRedisRepository) {
        this.districtRepository = districtRepository;
        this.districtStateRedisRepository = districtStateRedisRepository;
    }

    @Override
    public List<District> findAll() {
        return districtRepository.findAll();
    }

    @Override
    public District findByCode(String districtCode) {
        return districtRepository.findByCode(districtCode)
                .orElseThrow(() ->
                        new RuntimeException(format("'%s' is incorrect district code", districtCode)));
    }

    @Override
    public List<DistrictMapStateObject> getGeoData() {
        List<District> districts = findAll();
        List<DistrictState> states = findAllStates();

        return districts.stream()
                .map(district -> {
                    DistrictState state = states
                            .stream()
                            .filter(innerState -> relatesToDistrict(innerState, district))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("District not found"));

                    return DistrictMapStateObject.fromDistrictAndState(district, state);
                }).collect(Collectors.toList());
    }

    @Override
    public void initialize() {
        List<DistrictState> districtStates = findAllStates();
        List<District> districtsToBeCached = findAll();

        districtsToBeCached.removeIf(district ->
                districtStates
                        .stream()
                        .anyMatch(state -> relatesToDistrict(state, district)));

        List<DistrictState> statesToBeCached = districtsToBeCached
                .stream()
                .map(this::mapToDistrictState)
                .collect(Collectors.toList());

        districtStateRedisRepository.saveAll(statesToBeCached);
    }

    @Override
    public List<DistrictState> findAllStates() {
        return (List<DistrictState>) districtStateRedisRepository.findAll();
    }

    @Override
    public DistrictState getDistrictState(String districtCode) {
        Optional<DistrictState> districtState = districtStateRedisRepository.findById(districtCode);

        if (districtState.isPresent()) {
            return districtState.get();
        }

        District district = findByCode(districtCode);
        return districtStateRedisRepository.save(mapToDistrictState(district));
    }

    @Override
    public DistrictState updateDistrictState(DistrictState updatedState) {
        districtStateRedisRepository.deleteById(updatedState.getDistrictCode());
        return districtStateRedisRepository.save(updatedState);
    }

    private Boolean relatesToDistrict(DistrictState state, District district) {
        return state.getDistrictCode().equals(district.getCode());
    }

    private DistrictState mapToDistrictState(District district) {
        return DistrictState.builder()
                .districtCode(district.getCode())
                .districtName(district.getName())
                .alarmState(AlarmState.OFF)
                .build();
    }
}
