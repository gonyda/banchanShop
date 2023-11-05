package com.bbsk.banchanshop.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbsk.banchanshop.cart.entity.CartEntity;

public interface CartRepository extends JpaRepository<CartEntity, String>{

}
