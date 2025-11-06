package com.bomiora.shopping.wish.service;

import com.bomiora.shopping.wish.entity.Wish;
import com.bomiora.shopping.wish.repository.WishRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class WishService {
    
    private final WishRepository wishRepository;
    
    public WishService(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }
    
    /**
     * 찜하기 추가/제거 (토글)
     * @param mbId 사용자 ID
     * @param itId 상품 ID
     * @return true: 찜하기 추가됨, false: 찜하기 제거됨
     */
    @Transactional
    public boolean toggleWish(String mbId, String itId) {
        // 기존 찜하기 확인
        var existingWish = wishRepository.findByMbIdAndItId(mbId, itId);
        
        if (existingWish.isPresent()) {
            // 이미 찜하기가 있으면 삭제
            wishRepository.delete(existingWish.get());
            System.out.println("찜하기 삭제 - mbId: " + mbId + ", itId: " + itId);
            return false;
        } else {
            // 찜하기 추가
            Wish wish = new Wish();
            wish.setMbId(mbId);
            wish.setItId(itId);
            wish.setInfCode("");
            wish.setWiTime(LocalDateTime.now());
            wish.setWiIp("127.0.0.1"); // 실제로는 요청에서 가져와야 함
            
            wishRepository.save(wish);
            System.out.println("찜하기 추가 - mbId: " + mbId + ", itId: " + itId);
            return true;
        }
    }
    
    /**
     * 찜하기 추가
     * @param mbId 사용자 ID
     * @param itId 상품 ID
     * @return 저장된 Wish 엔티티
     */
    @Transactional
    public Wish addWish(String mbId, String itId) {
        // 이미 찜하기가 있는지 확인
        var existingWish = wishRepository.findByMbIdAndItId(mbId, itId);
        if (existingWish.isPresent()) {
            return existingWish.get();
        }
        
        Wish wish = new Wish();
        wish.setMbId(mbId);
        wish.setItId(itId);
        wish.setInfCode("");
        wish.setWiTime(LocalDateTime.now());
        wish.setWiIp("127.0.0.1");
        
        return wishRepository.save(wish);
    }
    
    /**
     * 찜하기 제거
     * @param mbId 사용자 ID
     * @param itId 상품 ID
     */
    @Transactional
    public void removeWish(String mbId, String itId) {
        wishRepository.deleteByMbIdAndItId(mbId, itId);
    }
    
    /**
     * 사용자의 찜목록 조회
     * @param mbId 사용자 ID
     * @return 찜목록
     */
    public List<Wish> getWishList(String mbId) {
        return wishRepository.findByMbIdOrderByWiTimeDesc(mbId);
    }
    
    /**
     * 사용자가 특정 상품을 찜했는지 확인
     * @param mbId 사용자 ID
     * @param itId 상품 ID
     * @return 찜하기 여부
     */
    public boolean isWished(String mbId, String itId) {
        return wishRepository.existsByMbIdAndItId(mbId, itId);
    }
}

