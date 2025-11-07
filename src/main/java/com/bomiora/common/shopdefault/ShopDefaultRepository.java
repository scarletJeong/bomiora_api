package com.bomiora.common.shopdefault;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopDefaultRepository extends JpaRepository<ShopDefault, Integer> {
    // 보통 bomiora_shop_default는 1개의 레코드만 있음
}

