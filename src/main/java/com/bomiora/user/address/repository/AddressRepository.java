package com.bomiora.user.address.repository;

import com.bomiora.user.address.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    
    /**
     * 회원의 배송지 조회 (ad_subject별로 최신 1개씩, 기본 배송지 우선)
     * 각 배송지 이름(ad_subject)별로 가장 최근 것만 가져오기
     */
    @Query(value = "SELECT a.* " +
           "FROM bomiora_shop_order_address a " +
           "INNER JOIN ( " +
           "  SELECT ad_subject, MAX(ad_id) as max_id " +
           "  FROM bomiora_shop_order_address " +
           "  WHERE mb_id = :mbId " +
           "  GROUP BY ad_subject " +
           ") b ON a.ad_subject = b.ad_subject AND a.ad_id = b.max_id " +
           "WHERE a.mb_id = :mbId " +
           "ORDER BY a.ad_default DESC, a.ad_id DESC",
           nativeQuery = true)
    List<Address> findByMbIdOrderByIsDefaultDescIdDesc(@Param("mbId") String mbId);
    
    /**
     * 회원의 특정 배송지 조회
     */
    @Query("SELECT a FROM Address a WHERE a.id = :id AND a.mbId = :mbId")
    Optional<Address> findByIdAndMbId(@Param("id") Long id, @Param("mbId") String mbId);
    
    /**
     * 회원의 기본 배송지 조회
     */
    @Query("SELECT a FROM Address a WHERE a.mbId = :mbId AND a.isDefault = 1")
    Optional<Address> findDefaultByMbId(@Param("mbId") String mbId);
    
    /**
     * 회원의 모든 배송지를 기본 배송지 해제
     */
    @Modifying
    @Query("UPDATE Address a SET a.isDefault = 0 WHERE a.mbId = :mbId")
    void clearDefaultByMbId(@Param("mbId") String mbId);
}

