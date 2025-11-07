package com.bomiora.common.shopdefault;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/shop")
public class ShopDefaultController {
    
    @Autowired
    private ShopDefaultService shopDefaultService;
    
    @GetMapping("/reservation-settings")
    public ResponseEntity<Map<String, Object>> getReservationSettings() {
        Map<String, Object> settings = shopDefaultService.getReservationSettings();
        return ResponseEntity.ok(settings);
    }
}

