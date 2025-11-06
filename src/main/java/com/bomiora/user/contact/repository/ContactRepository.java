package com.bomiora.user.contact.repository;

import com.bomiora.user.contact.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {
    
    /**
     * 사용자의 문의내역 조회 (모든 원글)
     * wr_is_comment = 0 (답변없음) 또는 1 (답변있음) 모두 조회
     */
    @Query("SELECT i FROM Contact i WHERE i.mbId = :mbId " +
           "ORDER BY i.wrDatetime DESC")
    List<Contact> findByMbId(@Param("mbId") String mbId);
    
    /**
     * 문의 상세 조회
     */
    @Query("SELECT i FROM Contact i WHERE i.wrId = :wrId")
    Optional<Contact> findByIdAndIsPost(@Param("wrId") Integer wrId);
    
    /**
     * 문의의 답변 목록 조회 (댓글만)
     * wr_is_comment = 1이고 wr_parent = wrId인 경우
     */
    @Query(value = "SELECT * FROM bomiora_write_online " +
                   "WHERE wr_parent = :wrId " +
                   "AND wr_is_comment = 1 " +
                   "ORDER BY wr_datetime ASC", 
           nativeQuery = true)
    List<Contact> findRepliesByWrId(@Param("wrId") Integer wrId);
    
    /**
     * 다음 wr_id 값 조회 (새 글 작성 시 사용)
     */
    @Query("SELECT MAX(i.wrId) FROM Contact i")
    Optional<Integer> findMaxWrId();
    
    /**
     * 다음 wr_num 값 조회 (새 글 작성 시 사용)
     */
    @Query("SELECT MAX(i.wrNum) FROM Contact i")
    Optional<Integer> findMaxWrNum();
}

