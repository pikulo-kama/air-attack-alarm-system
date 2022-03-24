package com.adrabazha.air.attack.alarm.system.repository;

import com.adrabazha.air.attack.alarm.system.model.domain.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByTelegramUserId(String userId);

    @Query(value = "SELECT a.* " +
            "FROM users a " +
            "         JOIN user_alert_subscription b ON a.id = b.user_id " +
            "WHERE b.district_id = :districtId", nativeQuery = true)
    List<User> findSubscribersByDistrict(@Param("districtId") Long districtId);

    @Query(value = "SELECT a.code as district_code, " +
            "              a.name as district_name, " +
            "              BOOL((SELECT CAST(COUNT(1) as integer) " +
            "                    FROM user_alert_subscription b " +
            "                    WHERE b.district_id = a.id " +
            "                      AND b.user_id = :userId)) as is_subscribed " +
            "            FROM districts a", nativeQuery = true)
    List<Map<String, Object>> findUserSubscription(@Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO user_alert_subscription " +
            "VALUES (:userId, :districtId)", nativeQuery = true)
    void addSubscriber(@Param("userId") Long userId, @Param("districtId") Long districtId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_alert_subscription " +
            "WHERE user_id = :userId AND district_id = :districtId", nativeQuery = true)
    void removeSubscriber(@Param("userId") Long userId, @Param("districtId") Long districtId);

    @Query(value = "SELECT BOOL(CAST(COUNT(1) as integer)) FROM user_alert_subscription " +
            "WHERE user_id = :userId AND district_id = :districtId", nativeQuery = true)
    Boolean isUserSubscribed(@Param("userId") Long userId, @Param("districtId") Long districtId);
}
