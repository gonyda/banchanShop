package com.bbsk.banchanshop.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbsk.banchanshop.cart.entity.CartItemEntity;

public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {
}
