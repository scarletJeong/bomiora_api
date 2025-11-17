package com.bomiora.user.address.service;

import com.bomiora.user.address.dto.AddressDTO;
import com.bomiora.user.address.entity.Address;
import com.bomiora.user.address.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressService {
    
    @Autowired
    private AddressRepository addressRepository;
    
    /**
     * 배송지 목록 조회
     */
    public List<AddressDTO> getAddressList(String mbId) {
        List<Address> addresses = addressRepository.findByMbIdOrderByIsDefaultDescIdDesc(mbId);
        return addresses.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * 배송지 상세 조회
     */
    public AddressDTO getAddressDetail(Long id, String mbId) {
        Address address = addressRepository.findByIdAndMbId(id, mbId)
                .orElseThrow(() -> new RuntimeException("배송지를 찾을 수 없습니다."));
        return convertToDTO(address);
    }
    
    /**
     * 배송지 추가
     */
    @Transactional
    public AddressDTO addAddress(AddressDTO dto) {
        // 기본 배송지로 설정하는 경우, 기존 기본 배송지 해제
        if (dto.getAdDefault() != null && dto.getAdDefault() == 1) {
            addressRepository.clearDefaultByMbId(dto.getMbId());
        }
        
        Address address = new Address();
        address.setMbId(dto.getMbId());
        address.setSubject(dto.getAdSubject());
        address.setIsDefault(dto.getAdDefault() != null ? dto.getAdDefault() : 0);
        address.setRecipientName(dto.getAdName());
        address.setRecipientTel(dto.getAdTel());
        address.setRecipientHp(dto.getAdHp());
        address.setZip1(dto.getAdZip1());
        address.setZip2(dto.getAdZip2());
        address.setAddress1(dto.getAdAddr1());
        address.setAddress2(dto.getAdAddr2());
        address.setAddress3(dto.getAdAddr3());
        address.setJibeon(dto.getAdJibeon());
        
        Address saved = addressRepository.save(address);
        return convertToDTO(saved);
    }
    
    /**
     * 배송지 수정
     */
    @Transactional
    public AddressDTO updateAddress(Long id, AddressDTO dto) {
        Address address = addressRepository.findByIdAndMbId(id, dto.getMbId())
                .orElseThrow(() -> new RuntimeException("배송지를 찾을 수 없습니다."));
        
        // 기본 배송지로 설정하는 경우, 기존 기본 배송지 해제
        if (dto.getAdDefault() != null && dto.getAdDefault() == 1) {
            addressRepository.clearDefaultByMbId(dto.getMbId());
        }
        
        // 정보 업데이트
        address.setSubject(dto.getAdSubject());
        address.setIsDefault(dto.getAdDefault() != null ? dto.getAdDefault() : 0);
        address.setRecipientName(dto.getAdName());
        address.setRecipientTel(dto.getAdTel());
        address.setRecipientHp(dto.getAdHp());
        address.setZip1(dto.getAdZip1());
        address.setZip2(dto.getAdZip2());
        address.setAddress1(dto.getAdAddr1());
        address.setAddress2(dto.getAdAddr2());
        address.setAddress3(dto.getAdAddr3());
        address.setJibeon(dto.getAdJibeon());
        
        Address updated = addressRepository.save(address);
        return convertToDTO(updated);
    }
    
    /**
     * 배송지 삭제
     */
    @Transactional
    public void deleteAddress(Long id, String mbId) {
        Address address = addressRepository.findByIdAndMbId(id, mbId)
                .orElseThrow(() -> new RuntimeException("배송지를 찾을 수 없습니다."));
        addressRepository.delete(address);
    }
    
    /**
     * Entity를 DTO로 변환
     */
    private AddressDTO convertToDTO(Address address) {
        AddressDTO dto = new AddressDTO();
        dto.setAdId(address.getId());
        dto.setMbId(address.getMbId());
        dto.setAdSubject(address.getSubject());
        dto.setAdDefault(address.getIsDefault());
        dto.setAdName(address.getRecipientName());
        dto.setAdTel(address.getRecipientTel());
        dto.setAdHp(address.getRecipientHp());
        dto.setAdZip1(address.getZip1());
        dto.setAdZip2(address.getZip2());
        dto.setAdAddr1(address.getAddress1());
        dto.setAdAddr2(address.getAddress2());
        dto.setAdAddr3(address.getAddress3());
        dto.setAdJibeon(address.getJibeon());
        return dto;
    }
}

