package com.bbsk.banchanshop.cart.repository;

import com.bbsk.banchanshop.cart.entity.CartItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CartRepositoryCustom {

    Page<CartItemEntity> findAllByPaging(String userId, Pageable pageable);
}
