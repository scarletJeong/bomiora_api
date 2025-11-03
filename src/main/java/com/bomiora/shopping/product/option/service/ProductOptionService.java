package com.bomiora.shopping.product.option.service;

import com.bomiora.shopping.product.option.dto.ProductOptionDTO;
import com.bomiora.shopping.product.option.entity.ProductOption;
import com.bomiora.shopping.product.option.repository.ProductOptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductOptionService {
    
    @Autowired
    private ProductOptionRepository optionRepository;
    
    /**
     * 제품 옵션 목록 조회 (사용 가능한 옵션만)
     */
    public List<ProductOptionDTO> getProductOptions(String productId) {
        List<ProductOption> options = optionRepository.findByProductIdAndUseFlag(productId, 1);
        
        return options.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Entity를 DTO로 변환
     */
    private ProductOptionDTO convertToDTO(ProductOption entity) {
        ProductOptionDTO dto = new ProductOptionDTO();
        dto.setId(entity.getId());
        dto.setProductId(entity.getProductId());
        dto.setOptionName(entity.getOptionName());
        dto.setDays(entity.getDays());
        dto.setPrice(entity.getPrice());
        dto.setStock(entity.getStock());
        dto.setType(entity.getType());
        
        return dto;
    }
}

