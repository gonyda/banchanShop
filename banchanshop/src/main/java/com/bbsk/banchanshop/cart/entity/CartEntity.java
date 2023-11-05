package com.bbsk.banchanshop.cart.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "CART")
public class CartEntity {
	
	@Id
	private String cartId;
	
	@Column(nullable = false)
	private int totalSum;
	
	@Column(nullable = false)
	private int totalQuantity;
}
