package com.bomiora.common.shopdefault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ShopDefaultService {
    
    @Autowired
    private ShopDefaultRepository shopDefaultRepository;
    
    public Map<String, Object> getReservationSettings() {
        Optional<ShopDefault> defaultOpt = shopDefaultRepository.findAll().stream().findFirst();
        
        if (defaultOpt.isEmpty()) {
            return new HashMap<>();
        }
        
        ShopDefault shopDefault = defaultOpt.get();
        
        Map<String, Object> settings = new HashMap<>();
        
        // 요일별 설정
        settings.put("monday", createDaySettings(
            shopDefault.getMondayStartTime(), 
            shopDefault.getMondayEndTime(), 
            shopDefault.getMondayActive()
        ));
        
        settings.put("tuesday", createDaySettings(
            shopDefault.getTuesdayStartTime(), 
            shopDefault.getTuesdayEndTime(), 
            shopDefault.getTuesdayActive()
        ));
        
        settings.put("wednesday", createDaySettings(
            shopDefault.getWednesdayStartTime(), 
            shopDefault.getWednesdayEndTime(), 
            shopDefault.getWednesdayActive()
        ));
        
        settings.put("thursday", createDaySettings(
            shopDefault.getThursdayStartTime(), 
            shopDefault.getThursdayEndTime(), 
            shopDefault.getThursdayActive()
        ));
        
        settings.put("friday", createDaySettings(
            shopDefault.getFridayStartTime(), 
            shopDefault.getFridayEndTime(), 
            shopDefault.getFridayActive()
        ));
        
        settings.put("saturday", createDaySettings(
            shopDefault.getSaturdayStartTime(), 
            shopDefault.getSaturdayEndTime(), 
            shopDefault.getSaturdayActive()
        ));
        
        settings.put("sunday", createDaySettings(
            shopDefault.getSundayStartTime(), 
            shopDefault.getSundayEndTime(), 
            shopDefault.getSundayActive()
        ));
        
        // 점심시간 설정
        Map<String, String> lunch = new HashMap<>();
        lunch.put("start_time", shopDefault.getLunchStartTime());
        lunch.put("end_time", shopDefault.getLunchEndTime());
        settings.put("lunch", lunch);
        
        // 휴일 설정
        settings.put("holiday", createDaySettings(
            shopDefault.getHolidayStartTime(), 
            shopDefault.getHolidayEndTime(), 
            shopDefault.getHolidayActive()
        ));
        
        // 예약 간격 및 인원 제한
        settings.put("relay_time", shopDefault.getRelayTime() != null ? shopDefault.getRelayTime() : 30);
        settings.put("limit_person", shopDefault.getLimitPerson() != null ? shopDefault.getLimitPerson() : 15);
        
        return settings;
    }
    
    private Map<String, Object> createDaySettings(String startTime, String endTime, Integer active) {
        Map<String, Object> day = new HashMap<>();
        day.put("start_time", startTime);
        day.put("end_time", endTime);
        day.put("active", active != null && active == 1);
        return day;
    }
}

