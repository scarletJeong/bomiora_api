package com.bomiora.shopping.product.controller;

import com.bomiora.shopping.product.dto.ProductDTO;
import com.bomiora.shopping.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    /**
     * 카테고리별 상품 목록 조회
     * GET /api/products/list?ca_id={categoryId}&it_kind={productKind}&page={page}&pageSize={pageSize}
     */
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getProductsByCategory(
            @RequestParam("ca_id") String categoryId,
            @RequestParam(required = false) String it_kind,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int pageSize) {
        try {
            System.out.println("상품 목록 조회 - categoryId: " + categoryId + 
                             ", it_kind: " + it_kind + 
                             ", page: " + page + 
                             ", pageSize: " + pageSize);
            
            List<ProductDTO> products = productService.getProductsByCategory(
                categoryId, it_kind, page, pageSize
            );
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", products);
            response.put("total", products.size());
            response.put("page", page);
            response.put("pageSize", pageSize);
            
            System.out.println("상품 목록 조회 완료 - 개수: " + products.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("상품 목록 조회 실패: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "상품 목록 조회 실패: " + e.getMessage());
            response.put("data", List.of());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * 상품 상세 정보 조회
     * GET /api/products/detail?id={productId}
     */
    @GetMapping("/detail")
    public ResponseEntity<Map<String, Object>> getProductDetail(
            @RequestParam("id") String productId) {
        try {
            System.out.println("상품 상세 조회 - productId: " + productId);
            
            ProductDTO product = productService.getProductDetail(productId);
            
            Map<String, Object> response = new HashMap<>();
            if (product != null) {
                response.put("success", true);
                response.put("data", product);
            } else {
                response.put("success", false);
                response.put("message", "상품을 찾을 수 없습니다");
            }
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("상품 상세 조회 실패: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "상품 상세 조회 실패: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * 인기 상품 목록 조회
     * GET /api/products/popular?limit={limit}
     */
    @GetMapping("/popular")
    public ResponseEntity<Map<String, Object>> getPopularProducts(
            @RequestParam(defaultValue = "10") int limit) {
        try {
            System.out.println("인기 상품 조회 - limit: " + limit);
            
            List<ProductDTO> products = productService.getPopularProducts(limit);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", products);
            
            System.out.println("인기 상품 조회 완료 - 개수: " + products.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("인기 상품 조회 실패: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "인기 상품 조회 실패: " + e.getMessage());
            response.put("data", List.of());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * 신상품 목록 조회
     * GET /api/products/new?limit={limit}
     */
    @GetMapping("/new")
    public ResponseEntity<Map<String, Object>> getNewProducts(
            @RequestParam(defaultValue = "10") int limit) {
        try {
            System.out.println("신상품 조회 - limit: " + limit);
            
            List<ProductDTO> products = productService.getNewProducts(limit);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", products);
            
            System.out.println("신상품 조회 완료 - 개수: " + products.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("신상품 조회 실패: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "신상품 조회 실패: " + e.getMessage());
            response.put("data", List.of());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

