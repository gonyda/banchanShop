package com.bbsk.banchanshop.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bbsk.banchanshop.cart.entity.CartItemEntity;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {
	CartItemEntity findByCartCartIdAndBanchanBanchanId(String cartId, Long banchanId);
}
