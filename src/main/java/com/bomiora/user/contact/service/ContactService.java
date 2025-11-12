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
    public List<Contact> getUserContacts(String mbId) {
        try {
            System.out.println("====================================");
            System.out.println("📋 [문의내역 조회] mbId: " + mbId);
            
            List<Contact> contacts = contactRepository.findByMbId(mbId);
            
            System.out.println("📊 [조회 결과] 총 " + contacts.size() + "건");
            
            if (!contacts.isEmpty()) {
                System.out.println("상위 3개 문의:");
                for (int i = 0; i < Math.min(3, contacts.size()); i++) {
                    Contact c = contacts.get(i);
                    System.out.println("  " + (i+1) + ". wr_id: " + c.getWrId() + 
                                     ", wr_is_comment: " + c.getWrIsComment() + 
                                     ", subject: " + c.getWrSubject());
                }
            }
            System.out.println("====================================");
            
            return contacts;
        } catch (Exception e) {
            System.out.println("❌ 문의내역 조회 오류: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * 문의 상세 조회
     */
    public Optional<Contact> getContactDetail(Integer wrId) {
        try {
            Optional<Contact> contact = contactRepository.findByIdAndIsPost(wrId);
            
            // 조회수 증가 (자신의 글은 제외하고 증가시킬 수 있지만, 일단 모두 증가)
            if (contact.isPresent()) {
                Contact c = contact.get();
                if (c.getWrHit() == null) {
                    c.setWrHit(0);
                }
                c.setWrHit(c.getWrHit() + 1);
                contactRepository.save(c);
            }
            
            return contact;
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
    public Contact createContact(Contact contact) {
        try {
            System.out.println("🔧 [ContactService] 받은 Contact 객체:");
            System.out.println("   wr_5: " + contact.getWr5());
            System.out.println("   wr_option: " + contact.getWrOption());
            
            // wr_id 생성 (최대값 + 1)
            Integer nextWrId = contactRepository.findMaxWrId()
                .map(max -> max + 1)
                .orElse(1);
            
            // wr_num 생성 (최대값 + 1, 새글은 고유한 wr_num)
            Integer nextWrNum = contactRepository.findMaxWrNum()
                .map(max -> max + 1)
                .orElse(1);
            
            System.out.println("🔢 생성된 ID/NUM:");
            System.out.println("   nextWrId: " + nextWrId);
            System.out.println("   nextWrNum: " + nextWrNum);
            
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
            //  wr_option 기본값 (설정되지 않았으면 빈 문자열)
            if (contact.getWrOption() == null) {
                System.out.println("⚠️ wr_option이 null이어서 빈 문자열로 설정");
                contact.setWrOption("");
            } else {
                System.out.println("✅ wr_option 값 유지: " + contact.getWrOption());
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
                System.out.println("⚠️ wr_5가 null이어서 빈 문자열로 설정");
                contact.setWr5("");
            } else {
                System.out.println("✅ wr_5 값 유지: " + contact.getWr5());
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
            
            System.out.println("💾 [DB 저장 직전] 최종 값:");
            System.out.println("   wr_1: " + contact.getWr1());
            System.out.println("   wr_5: " + contact.getWr5());
            System.out.println("   wr_option: " + contact.getWrOption());
            System.out.println("   wr_num: " + contact.getWrNum());
            
            // 저장 후 wr_parent 업데이트
            Contact saved = contactRepository.save(contact);
            saved.setWrParent(saved.getWrId());
            contactRepository.save(saved);
            
            System.out.println("✅ 문의 작성 완료 - wrId: " + saved.getWrId());
            System.out.println("   최종 wr_1: " + saved.getWr1());
            System.out.println("   최종 wr_5: " + saved.getWr5());
            System.out.println("   최종 wr_option: " + saved.getWrOption());
            System.out.println("   최종 wr_num: " + saved.getWrNum());
            return saved;
        } catch (Exception e) {
            System.out.println("❌ 문의 작성 오류: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * 문의 수정
     */
    @Transactional
    public Contact updateContact(Contact contact) {
        try {
            System.out.println("✏️ [ContactService] 문의 수정 시작");
            System.out.println("   wr_id: " + contact.getWrId());
            System.out.println("   wr_subject: " + contact.getWrSubject());
            
            // 수정 시간 업데이트
            contact.setWrLast(LocalDateTime.now());
            
            Contact updated = contactRepository.save(contact);
            
            System.out.println("✅ 문의 수정 완료 - wrId: " + updated.getWrId());
            return updated;
        } catch (Exception e) {
            System.out.println("❌ 문의 수정 오류: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * 문의 답변 목록 조회 (댓글)
     */
    public List<Contact> getContactReplies(Integer wrId) {
        try {
            System.out.println("====================================");
            System.out.println("💬 [답변 조회 시작] wrId: " + wrId);
            System.out.println("====================================");
            
            List<Contact> replies = contactRepository.findRepliesByWrId(wrId);
            
            System.out.println("📊 [답변 조회 결과] 개수: " + replies.size());
            
            if (replies.isEmpty()) {
                System.out.println("⚠️ [답변 조회] 답변이 없습니다.");
                System.out.println("확인 사항:");
                System.out.println("  1. DB에 wr_parent = " + wrId + " 인 데이터가 있는지?");
                System.out.println("  2. wr_is_comment = 1 인지?");
            } else {
                System.out.println("✅ [답변 조회] 답변 목록:");
                for (Contact reply : replies) {
                    System.out.println("  - wr_id: " + reply.getWrId() + 
                                     ", wr_parent: " + reply.getWrParent() + 
                                     ", wr_is_comment: " + reply.getWrIsComment() +
                                     ", wr_content: " + (reply.getWrContent() != null ? 
                                         reply.getWrContent().substring(0, Math.min(50, reply.getWrContent().length())) : "null"));
                }
            }
            
            System.out.println("====================================");
            return replies;
        } catch (Exception e) {
            System.out.println("❌ 답변 목록 조회 오류: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}

