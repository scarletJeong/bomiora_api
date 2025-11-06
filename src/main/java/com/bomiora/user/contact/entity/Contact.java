package com.bomiora.user.contact.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bomiora_write_online")
public class Contact {
    
    @Id
    @Column(name = "wr_id")
    private Integer wrId;
    
    @Column(name = "wr_num")
    private Integer wrNum;
    
    @Column(name = "wr_reply", length = 10)
    private String wrReply;
    
    @Column(name = "wr_parent")
    private Integer wrParent;
    
    @Column(name = "wr_comment")
    private Integer wrComment;
    
    @Column(name = "wr_comment_reply", length = 5)
    private String wrCommentReply;
    
    @Column(name = "wr_is_comment")
    private Integer wrIsComment;
    
    @Column(name = "ca_name", length = 255)
    private String caName;
    
    @Column(name = "wr_option", length = 20)
    private String wrOption;
    
    @Column(name = "wr_subject", length = 255)
    private String wrSubject;
    
    @Column(name = "wr_content", columnDefinition = "TEXT")
    private String wrContent;
    
    @Column(name = "wr_seo_title", length = 255)
    private String wrSeoTitle;
    
    @Column(name = "wr_link1", columnDefinition = "TEXT")
    private String wrLink1;
    
    @Column(name = "wr_link2", columnDefinition = "TEXT")
    private String wrLink2;
    
    @Column(name = "wr_link1_hit")
    private Integer wrLink1Hit;
    
    @Column(name = "wr_link2_hit")
    private Integer wrLink2Hit;
    
    @Column(name = "wr_hit")
    private Integer wrHit;
    
    @Column(name = "wr_good")
    private Integer wrGood;
    
    @Column(name = "wr_nogood")
    private Integer wrNogood;
    
    @Column(name = "mb_id", length = 20)
    private String mbId;
    
    @Column(name = "wr_password", length = 255)
    private String wrPassword;
    
    @Column(name = "wr_name", length = 255)
    private String wrName;
    
    @Column(name = "wr_email", length = 255)
    private String wrEmail;
    
    @Column(name = "wr_homepage", length = 255)
    private String wrHomepage;
    
    @Column(name = "wr_datetime")
    private LocalDateTime wrDatetime;
    
    @Column(name = "wr_file")
    private Integer wrFile;
    
    @Column(name = "wr_last")
    private LocalDateTime wrLast;
    
    @Column(name = "wr_ip", length = 255)
    private String wrIp;
    
    @Column(name = "wr_facebook_user", length = 255)
    private String wrFacebookUser;
    
    @Column(name = "wr_twitter_user", length = 255)
    private String wrTwitterUser;
    
    @Column(name = "wr_1", length = 255)
    private String wr1;
    
    @Column(name = "wr_2", length = 255)
    private String wr2;
    
    @Column(name = "wr_3", length = 255)
    private String wr3;
    
    @Column(name = "wr_4", length = 255)
    private String wr4;
    
    @Column(name = "wr_5", length = 255)
    private String wr5;
    
    @Column(name = "wr_6", length = 255)
    private String wr6;
    
    @Column(name = "wr_7", columnDefinition = "MEDIUMTEXT")
    private String wr7;
    
    @Column(name = "wr_8", length = 255)
    private String wr8;
    
    @Column(name = "wr_9", length = 255)
    private String wr9;
    
    @Column(name = "wr_10", length = 255)
    private String wr10;
    
    // 기본 생성자
    public Contact() {}
    
    // Getters and Setters
    public Integer getWrId() { return wrId; }
    public void setWrId(Integer wrId) { this.wrId = wrId; }
    
    public Integer getWrNum() { return wrNum; }
    public void setWrNum(Integer wrNum) { this.wrNum = wrNum; }
    
    public String getWrReply() { return wrReply; }
    public void setWrReply(String wrReply) { this.wrReply = wrReply; }
    
    public Integer getWrParent() { return wrParent; }
    public void setWrParent(Integer wrParent) { this.wrParent = wrParent; }
    
    public Integer getWrComment() { return wrComment; }
    public void setWrComment(Integer wrComment) { this.wrComment = wrComment; }
    
    public String getWrCommentReply() { return wrCommentReply; }
    public void setWrCommentReply(String wrCommentReply) { this.wrCommentReply = wrCommentReply; }
    
    public Integer getWrIsComment() { return wrIsComment; }
    public void setWrIsComment(Integer wrIsComment) { this.wrIsComment = wrIsComment; }
    
    public String getCaName() { return caName; }
    public void setCaName(String caName) { this.caName = caName; }
    
    public String getWrOption() { return wrOption; }
    public void setWrOption(String wrOption) { this.wrOption = wrOption; }
    
    public String getWrSubject() { return wrSubject; }
    public void setWrSubject(String wrSubject) { this.wrSubject = wrSubject; }
    
    public String getWrContent() { return wrContent; }
    public void setWrContent(String wrContent) { this.wrContent = wrContent; }
    
    public String getWrSeoTitle() { return wrSeoTitle; }
    public void setWrSeoTitle(String wrSeoTitle) { this.wrSeoTitle = wrSeoTitle; }
    
    public String getWrLink1() { return wrLink1; }
    public void setWrLink1(String wrLink1) { this.wrLink1 = wrLink1; }
    
    public String getWrLink2() { return wrLink2; }
    public void setWrLink2(String wrLink2) { this.wrLink2 = wrLink2; }
    
    public Integer getWrLink1Hit() { return wrLink1Hit; }
    public void setWrLink1Hit(Integer wrLink1Hit) { this.wrLink1Hit = wrLink1Hit; }
    
    public Integer getWrLink2Hit() { return wrLink2Hit; }
    public void setWrLink2Hit(Integer wrLink2Hit) { this.wrLink2Hit = wrLink2Hit; }
    
    public Integer getWrHit() { return wrHit; }
    public void setWrHit(Integer wrHit) { this.wrHit = wrHit; }
    
    public Integer getWrGood() { return wrGood; }
    public void setWrGood(Integer wrGood) { this.wrGood = wrGood; }
    
    public Integer getWrNogood() { return wrNogood; }
    public void setWrNogood(Integer wrNogood) { this.wrNogood = wrNogood; }
    
    public String getMbId() { return mbId; }
    public void setMbId(String mbId) { this.mbId = mbId; }
    
    public String getWrPassword() { return wrPassword; }
    public void setWrPassword(String wrPassword) { this.wrPassword = wrPassword; }
    
    public String getWrName() { return wrName; }
    public void setWrName(String wrName) { this.wrName = wrName; }
    
    public String getWrEmail() { return wrEmail; }
    public void setWrEmail(String wrEmail) { this.wrEmail = wrEmail; }
    
    public String getWrHomepage() { return wrHomepage; }
    public void setWrHomepage(String wrHomepage) { this.wrHomepage = wrHomepage; }
    
    public LocalDateTime getWrDatetime() { return wrDatetime; }
    public void setWrDatetime(LocalDateTime wrDatetime) { this.wrDatetime = wrDatetime; }
    
    public Integer getWrFile() { return wrFile; }
    public void setWrFile(Integer wrFile) { this.wrFile = wrFile; }
    
    public LocalDateTime getWrLast() { return wrLast; }
    public void setWrLast(LocalDateTime wrLast) { this.wrLast = wrLast; }
    
    public String getWrIp() { return wrIp; }
    public void setWrIp(String wrIp) { this.wrIp = wrIp; }
    
    public String getWrFacebookUser() { return wrFacebookUser; }
    public void setWrFacebookUser(String wrFacebookUser) { this.wrFacebookUser = wrFacebookUser; }
    
    public String getWrTwitterUser() { return wrTwitterUser; }
    public void setWrTwitterUser(String wrTwitterUser) { this.wrTwitterUser = wrTwitterUser; }
    
    public String getWr1() { return wr1; }
    public void setWr1(String wr1) { this.wr1 = wr1; }
    
    public String getWr2() { return wr2; }
    public void setWr2(String wr2) { this.wr2 = wr2; }
    
    public String getWr3() { return wr3; }
    public void setWr3(String wr3) { this.wr3 = wr3; }
    
    public String getWr4() { return wr4; }
    public void setWr4(String wr4) { this.wr4 = wr4; }
    
    public String getWr5() { return wr5; }
    public void setWr5(String wr5) { this.wr5 = wr5; }
    
    public String getWr6() { return wr6; }
    public void setWr6(String wr6) { this.wr6 = wr6; }
    
    public String getWr7() { return wr7; }
    public void setWr7(String wr7) { this.wr7 = wr7; }
    
    public String getWr8() { return wr8; }
    public void setWr8(String wr8) { this.wr8 = wr8; }
    
    public String getWr9() { return wr9; }
    public void setWr9(String wr9) { this.wr9 = wr9; }
    
    public String getWr10() { return wr10; }
    public void setWr10(String wr10) { this.wr10 = wr10; }
    
    /**
     * 답변이 있는지 확인 (댓글이 있으면 답변 있음)
     */
    public boolean hasReply() {
        return wrComment != null && wrComment > 0;
    }
    
    /**
     * 게시글인지 확인 (댓글이 아닌 경우)
     */
    public boolean isPost() {
        return wrIsComment == null || wrIsComment == 0;
    }
}

