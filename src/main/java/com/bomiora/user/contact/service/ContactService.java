package com.bomiora.user.contact.service;

import com.bomiora.user.contact.entity.Contact;
import com.bomiora.user.contact.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ContactService {
    
    @Autowired
    private ContactRepository contactRepository;
    
    /**
     * 사용자의 문의내역 조회
     */
    public List<Contact> getUserInquiries(String mbId) {
        try {
            return contactRepository.findByMbId(mbId);
        } catch (Exception e) {
            System.out.println("❌ 문의내역 조회 오류: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * 문의 상세 조회
     */
    public Optional<Contact> getInquiryDetail(Integer wrId) {
        try {
            Optional<Contact> inquiry = contactRepository.findByIdAndIsPost(wrId);
            
            // 조회수 증가 (자신의 글은 제외하고 증가시킬 수 있지만, 일단 모두 증가)
            if (inquiry.isPresent()) {
                Contact i = inquiry.get();
                if (i.getWrHit() == null) {
                    i.setWrHit(0);
                }
                i.setWrHit(i.getWrHit() + 1);
                contactRepository.save(i);
            }
            
            return inquiry;
        } catch (Exception e) {
            System.out.println("❌ 문의 상세 조회 오류: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * 문의 작성
     */
    @Transactional
    public Contact createInquiry(Contact contact) {
        try {
            // wr_id 생성 (최대값 + 1)
            Integer nextWrId = contactRepository.findMaxWrId()
                .map(max -> max + 1)
                .orElse(1);
            
            // wr_num 생성 (최대값 + 1, 새글은 고유한 wr_num)
            Integer nextWrNum = contactRepository.findMaxWrNum()
                .map(max -> max + 1)
                .orElse(1);
            
            contact.setWrId(nextWrId);
            contact.setWrNum(nextWrNum);
            contact.setWrReply(""); // 새글은 빈 문자열
            contact.setWrParent(nextWrId); // 초기에는 자기 자신
            contact.setWrComment(0); // 댓글 수 초기값
            contact.setWrCommentReply(""); // 댓글 답변 필드
            contact.setWrIsComment(0); // 게시글
            contact.setWrHit(0); // 조회수 초기값
            contact.setWrGood(0);
            contact.setWrNogood(0);
            contact.setWrLink1Hit(0);
            contact.setWrLink2Hit(0);
            contact.setWrFile(0); // 파일 첨부 개수
            contact.setWrDatetime(LocalDateTime.now());
            contact.setWrLast(LocalDateTime.now());
            
            // NOT NULL 필드들 기본값 설정
            if (contact.getCaName() == null) {
                contact.setCaName("");
            }
            if (contact.getWrSeoTitle() == null) {
                contact.setWrSeoTitle("");
            }
            if (contact.getWrLink1() == null) {
                contact.setWrLink1("");
            }
            if (contact.getWrLink2() == null) {
                contact.setWrLink2("");
            }
            if (contact.getWrPassword() == null) {
                contact.setWrPassword("");
            }
            if (contact.getWrHomepage() == null) {
                contact.setWrHomepage("");
            }
            if (contact.getWrFacebookUser() == null) {
                contact.setWrFacebookUser("");
            }
            if (contact.getWrTwitterUser() == null) {
                contact.setWrTwitterUser("");
            }
            if (contact.getWr1() == null) {
                contact.setWr1("");
            }
            if (contact.getWr2() == null) {
                contact.setWr2("");
            }
            if (contact.getWr3() == null) {
                contact.setWr3("");
            }
            if (contact.getWr4() == null) {
                contact.setWr4("");
            }
            if (contact.getWr5() == null) {
                contact.setWr5("");
            }
            if (contact.getWr6() == null) {
                contact.setWr6("");
            }
            if (contact.getWr7() == null) {
                contact.setWr7("");
            }
            if (contact.getWr8() == null) {
                contact.setWr8("");
            }
            if (contact.getWr9() == null) {
                contact.setWr9("");
            }
            if (contact.getWr10() == null) {
                contact.setWr10("");
            }
            
            // 저장 후 wr_parent 업데이트
            Contact saved = contactRepository.save(contact);
            saved.setWrParent(saved.getWrId());
            contactRepository.save(saved);
            
            System.out.println("✅ 문의 작성 완료 - wrId: " + saved.getWrId());
            return saved;
        } catch (Exception e) {
            System.out.println("❌ 문의 작성 오류: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * 문의 답변 목록 조회 (댓글)
     */
    public List<Contact> getInquiryReplies(Integer wrId) {
        try {
            return contactRepository.findRepliesByWrId(wrId);
        } catch (Exception e) {
            System.out.println("❌ 답변 목록 조회 오류: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}

