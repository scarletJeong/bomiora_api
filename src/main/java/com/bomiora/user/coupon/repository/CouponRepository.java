package com.bomiora.user.coupon.repository;

import com.bomiora.user.coupon.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer> {
    
    /**
     * 사용자의 모든 쿠폰 조회
     */
    @Query("SELECT c FROM Coupon c WHERE c.userId = :userId ORDER BY c.endDate DESC, c.no DESC")
    List<Coupon> findByUserId(@Param("userId") String userId);
    
    /**
     * 사용 가능한 쿠폰 조회 (현재 날짜가 유효기간 내이고, 사용하지 않은 쿠폰)
     */
    @Query("SELECT c FROM Coupon c WHERE c.userId = :userId " +
           "AND c.startDate <= :today AND c.endDate >= :today " +
           "AND (c.orderId IS NULL OR c.orderId = 0) " +
           "ORDER BY c.endDate ASC, c.no DESC")
    List<Coupon> findAvailableCoupons(@Param("userId") String userId, @Param("today") LocalDate today);
    
    /**
     * 사용한 쿠폰 조회 (orderId가 있는 쿠폰)
     */
    @Query("SELECT c FROM Coupon c WHERE c.userId = :userId " +
           "AND c.orderId IS NOT NULL AND c.orderId > 0 " +
           "ORDER BY c.datetime DESC, c.no DESC")
    List<Coupon> findUsedCoupons(@Param("userId") String userId);
    
    /**
     * 만료된 쿠폰 조회 (유효기간이 지났고, 사용하지 않은 쿠폰)
     */
    @Query("SELECT c FROM Coupon c WHERE c.userId = :userId " +
           "AND c.endDate < :today " +
           "AND (c.orderId IS NULL OR c.orderId = 0) " +
           "ORDER BY c.endDate DESC, c.no DESC")
    List<Coupon> findExpiredCoupons(@Param("userId") String userId, @Param("today") LocalDate today);
    
    /**
     * 쿠폰 코드(cp_id)로 조회
     */
    @Query("SELECT c FROM Coupon c WHERE c.id = :couponId")
    Optional<Coupon> findByCouponId(@Param("couponId") String couponId);
    
    /**
     * 쿠폰 코드(cp_id)와 사용자 ID로 조회 (사용자가 이미 등록한 쿠폰인지 확인)
     */
    @Query("SELECT c FROM Coupon c WHERE c.id = :couponId AND c.userId = :userId")
    Optional<Coupon> findByCouponIdAndUserId(@Param("couponId") String couponId, @Param("userId") String userId);
}

