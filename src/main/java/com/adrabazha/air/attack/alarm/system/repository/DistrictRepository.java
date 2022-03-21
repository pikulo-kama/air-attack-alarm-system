package com.adrabazha.air.attack.alarm.system.repository;

import com.adrabazha.air.attack.alarm.system.model.domain.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {

    Optional<District> findByCode(String districtCode);
}
