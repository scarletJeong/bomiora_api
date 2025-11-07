package com.bomiora.common.shopdefault;

import jakarta.persistence.*;

@Entity
@Table(name = "bomiora_shop_default")
public class ShopDefault {
    
    // Primary Key로 사용할 임시 필드 (설정 테이블이므로 단일 row)
    @Id
    @Column(name = "de_rsvt_grelay_time", insertable = false, updatable = false)
    private Integer id;
    
    // 월요일 예약 설정
    @Column(name = "de_rsvt_mon_stime")
    private String mondayStartTime;
    
    @Column(name = "de_rsvt_mon_etime")
    private String mondayEndTime;
    
    @Column(name = "de_rsvt_mon_act")
    private Integer mondayActive;
    
    // 화요일 예약 설정
    @Column(name = "de_rsvt_tue_stime")
    private String tuesdayStartTime;
    
    @Column(name = "de_rsvt_tue_etime")
    private String tuesdayEndTime;
    
    @Column(name = "de_rsvt_tue_act")
    private Integer tuesdayActive;
    
    // 수요일 예약 설정
    @Column(name = "de_rsvt_wed_stime")
    private String wednesdayStartTime;
    
    @Column(name = "de_rsvt_wed_etime")
    private String wednesdayEndTime;
    
    @Column(name = "de_rsvt_wed_act")
    private Integer wednesdayActive;
    
    // 목요일 예약 설정
    @Column(name = "de_rsvt_thu_stime")
    private String thursdayStartTime;
    
    @Column(name = "de_rsvt_thu_etime")
    private String thursdayEndTime;
    
    @Column(name = "de_rsvt_thu_act")
    private Integer thursdayActive;
    
    // 금요일 예약 설정
    @Column(name = "de_rsvt_fri_stime")
    private String fridayStartTime;
    
    @Column(name = "de_rsvt_fri_etime")
    private String fridayEndTime;
    
    @Column(name = "de_rsvt_fri_act")
    private Integer fridayActive;
    
    // 토요일 예약 설정
    @Column(name = "de_rsvt_sat_stime")
    private String saturdayStartTime;
    
    @Column(name = "de_rsvt_sat_etime")
    private String saturdayEndTime;
    
    @Column(name = "de_rsvt_sat_act")
    private Integer saturdayActive;
    
    // 일요일 예약 설정
    @Column(name = "de_rsvt_sun_stime")
    private String sundayStartTime;
    
    @Column(name = "de_rsvt_sun_etime")
    private String sundayEndTime;
    
    @Column(name = "de_rsvt_sun_act")
    private Integer sundayActive;
    
    // 점심시간 설정
    @Column(name = "de_rsvt_lunch_stime")
    private String lunchStartTime;
    
    @Column(name = "de_rsvt_lunch_etime")
    private String lunchEndTime;
    
    // 휴일 설정
    @Column(name = "de_rsvt_holiday_stime")
    private String holidayStartTime;
    
    @Column(name = "de_rsvt_holiday_etime")
    private String holidayEndTime;
    
    @Column(name = "de_rsvt_holiday_act")
    private Integer holidayActive;
    
    // 예약 간격 (분)
    @Column(name = "de_rsvt_grelay_time")
    private Integer relayTime;
    
    // 예약 인원 제한
    @Column(name = "de_rsvt_limit_person")
    private Integer limitPerson;
    
    // Getters and Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getMondayStartTime() {
        return mondayStartTime;
    }
    
    public void setMondayStartTime(String mondayStartTime) {
        this.mondayStartTime = mondayStartTime;
    }
    
    public String getMondayEndTime() {
        return mondayEndTime;
    }
    
    public void setMondayEndTime(String mondayEndTime) {
        this.mondayEndTime = mondayEndTime;
    }
    
    public Integer getMondayActive() {
        return mondayActive;
    }
    
    public void setMondayActive(Integer mondayActive) {
        this.mondayActive = mondayActive;
    }
    
    public String getTuesdayStartTime() {
        return tuesdayStartTime;
    }
    
    public void setTuesdayStartTime(String tuesdayStartTime) {
        this.tuesdayStartTime = tuesdayStartTime;
    }
    
    public String getTuesdayEndTime() {
        return tuesdayEndTime;
    }
    
    public void setTuesdayEndTime(String tuesdayEndTime) {
        this.tuesdayEndTime = tuesdayEndTime;
    }
    
    public Integer getTuesdayActive() {
        return tuesdayActive;
    }
    
    public void setTuesdayActive(Integer tuesdayActive) {
        this.tuesdayActive = tuesdayActive;
    }
    
    public String getWednesdayStartTime() {
        return wednesdayStartTime;
    }
    
    public void setWednesdayStartTime(String wednesdayStartTime) {
        this.wednesdayStartTime = wednesdayStartTime;
    }
    
    public String getWednesdayEndTime() {
        return wednesdayEndTime;
    }
    
    public void setWednesdayEndTime(String wednesdayEndTime) {
        this.wednesdayEndTime = wednesdayEndTime;
    }
    
    public Integer getWednesdayActive() {
        return wednesdayActive;
    }
    
    public void setWednesdayActive(Integer wednesdayActive) {
        this.wednesdayActive = wednesdayActive;
    }
    
    public String getThursdayStartTime() {
        return thursdayStartTime;
    }
    
    public void setThursdayStartTime(String thursdayStartTime) {
        this.thursdayStartTime = thursdayStartTime;
    }
    
    public String getThursdayEndTime() {
        return thursdayEndTime;
    }
    
    public void setThursdayEndTime(String thursdayEndTime) {
        this.thursdayEndTime = thursdayEndTime;
    }
    
    public Integer getThursdayActive() {
        return thursdayActive;
    }
    
    public void setThursdayActive(Integer thursdayActive) {
        this.thursdayActive = thursdayActive;
    }
    
    public String getFridayStartTime() {
        return fridayStartTime;
    }
    
    public void setFridayStartTime(String fridayStartTime) {
        this.fridayStartTime = fridayStartTime;
    }
    
    public String getFridayEndTime() {
        return fridayEndTime;
    }
    
    public void setFridayEndTime(String fridayEndTime) {
        this.fridayEndTime = fridayEndTime;
    }
    
    public Integer getFridayActive() {
        return fridayActive;
    }
    
    public void setFridayActive(Integer fridayActive) {
        this.fridayActive = fridayActive;
    }
    
    public String getSaturdayStartTime() {
        return saturdayStartTime;
    }
    
    public void setSaturdayStartTime(String saturdayStartTime) {
        this.saturdayStartTime = saturdayStartTime;
    }
    
    public String getSaturdayEndTime() {
        return saturdayEndTime;
    }
    
    public void setSaturdayEndTime(String saturdayEndTime) {
        this.saturdayEndTime = saturdayEndTime;
    }
    
    public Integer getSaturdayActive() {
        return saturdayActive;
    }
    
    public void setSaturdayActive(Integer saturdayActive) {
        this.saturdayActive = saturdayActive;
    }
    
    public String getSundayStartTime() {
        return sundayStartTime;
    }
    
    public void setSundayStartTime(String sundayStartTime) {
        this.sundayStartTime = sundayStartTime;
    }
    
    public String getSundayEndTime() {
        return sundayEndTime;
    }
    
    public void setSundayEndTime(String sundayEndTime) {
        this.sundayEndTime = sundayEndTime;
    }
    
    public Integer getSundayActive() {
        return sundayActive;
    }
    
    public void setSundayActive(Integer sundayActive) {
        this.sundayActive = sundayActive;
    }
    
    public String getLunchStartTime() {
        return lunchStartTime;
    }
    
    public void setLunchStartTime(String lunchStartTime) {
        this.lunchStartTime = lunchStartTime;
    }
    
    public String getLunchEndTime() {
        return lunchEndTime;
    }
    
    public void setLunchEndTime(String lunchEndTime) {
        this.lunchEndTime = lunchEndTime;
    }
    
    public String getHolidayStartTime() {
        return holidayStartTime;
    }
    
    public void setHolidayStartTime(String holidayStartTime) {
        this.holidayStartTime = holidayStartTime;
    }
    
    public String getHolidayEndTime() {
        return holidayEndTime;
    }
    
    public void setHolidayEndTime(String holidayEndTime) {
        this.holidayEndTime = holidayEndTime;
    }
    
    public Integer getHolidayActive() {
        return holidayActive;
    }
    
    public void setHolidayActive(Integer holidayActive) {
        this.holidayActive = holidayActive;
    }
    
    public Integer getRelayTime() {
        return relayTime;
    }
    
    public void setRelayTime(Integer relayTime) {
        this.relayTime = relayTime;
    }
    
    public Integer getLimitPerson() {
        return limitPerson;
    }
    
    public void setLimitPerson(Integer limitPerson) {
        this.limitPerson = limitPerson;
    }
}

