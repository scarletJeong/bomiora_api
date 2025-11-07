package com.bomiora.shopping.event.service;

import com.bomiora.shopping.event.entity.Event;
import com.bomiora.shopping.event.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {
    
    @Autowired
    private EventRepository eventRepository;
    
    /**
     * 진행중인 이벤트 목록 조회
     */
    public List<Event> getActiveEvents() {
        List<Event> events = eventRepository.findActiveEvents();
        
        // 실제로 날짜 범위에 맞는 이벤트만 필터링
        return events.stream()
                .filter(Event::isActive)
                .collect(Collectors.toList());
    }
    
    /**
     * 종료된 이벤트 목록 조회
     */
    public List<Event> getEndedEvents() {
        return eventRepository.findEndedEvents();
    }
    
    /**
     * 이벤트 상세 조회 (조회수 증가)
     */
    @Transactional
    public Optional<Event> getEventDetail(Integer wrId) {
        Optional<Event> eventOpt = eventRepository.findByWrId(wrId);
        
        eventOpt.ifPresent(event -> {
            // 조회수 증가
            if (event.getWrHit() == null) {
                event.setWrHit(0);
            }
            event.setWrHit(event.getWrHit() + 1);
            eventRepository.save(event);
        });
        
        return eventOpt;
    }
}
