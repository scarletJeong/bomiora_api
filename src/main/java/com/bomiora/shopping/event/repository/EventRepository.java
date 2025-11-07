package com.bomiora.shopping.event.repository;

import com.bomiora.shopping.event.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    
    /**
     * 진행중인 이벤트 조회 (ca_name = '진행중인 이벤트')
     */
    @Query("SELECT e FROM Event e WHERE e.caName = '진행중인 이벤트' ORDER BY e.wrNum DESC")
    List<Event> findActiveEvents();
    
    /**
     * 종료된 이벤트 조회 (ca_name != '진행중인 이벤트' 또는 NULL)
     */
    @Query("SELECT e FROM Event e WHERE e.caName IS NULL OR e.caName != '진행중인 이벤트' ORDER BY e.wrNum")
    List<Event> findEndedEvents();
    
    /**
     * 이벤트 상세 조회
     */
    @Query("SELECT e FROM Event e WHERE e.wrId = :wrId")
    Optional<Event> findByWrId(Integer wrId);
}

