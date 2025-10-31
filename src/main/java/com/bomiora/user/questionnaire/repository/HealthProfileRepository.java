package com.bomiora.user.questionnaire.repository;

import com.bomiora.user.questionnaire.entity.HealthProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HealthProfileRepository extends JpaRepository<HealthProfile, Long> {
    
    /**
     * 사용자 ID로 문진표 조회
     * @param mbId 사용자 ID
     * @return 문진표 정보
     */
    Optional<HealthProfile> findByMbId(String mbId);
    
    /**
     * 사용자 ID로 문진표 존재 여부 확인
     * @param mbId 사용자 ID
     * @return 존재 여부
     */
    boolean existsByMbId(String mbId);
    
    /**
     * 사용자 ID로 문진표 삭제
     * @param mbId 사용자 ID
     */
    void deleteByMbId(String mbId);
    
    /**
     * 사용자 ID와 문진표 번호로 문진표 조회 (수정 시 사용)
     * @param pfNo 문진표 번호
     * @param mbId 사용자 ID
     * @return 문진표 정보
     */
    @Query("SELECT hp FROM HealthProfile hp WHERE hp.pfNo = :pfNo AND hp.mbId = :mbId")
    Optional<HealthProfile> findByPfNoAndMbId(@Param("pfNo") Long pfNo, @Param("mbId") String mbId);
    
    /**
     * 최근 작성된 문진표 조회 (사용자별)
     * @param mbId 사용자 ID
     * @return 최근 문진표 정보
     */
    @Query("SELECT hp FROM HealthProfile hp WHERE hp.mbId = :mbId ORDER BY hp.pfWdatetime DESC")
    Optional<HealthProfile> findLatestByMbId(@Param("mbId") String mbId);
}
