package com.bbsk.banchanshop.cart.entity;

import com.bbsk.banchanshop.banchan.entity.BanchanEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "CART_ITEM")
public class CartItemEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cartItemId;
	
	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cart_id")
	private CartEntity cart;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "banchan_id")
	private BanchanEntity banchan;
	
	@Column(nullable = false)
	private int banchanQuantity;
	
	@Column(nullable = false)
	private int banchanTotalPrice;

	public CartItemEntity updateCartItem(CartEntity cart, BanchanEntity banchan, int itemQuantity) {
		this.cart = cart;
		this.banchan = banchan;
		this.banchanQuantity = itemQuantity;
		this.banchanTotalPrice = itemQuantity * banchan.getBanchanPrice();

		return this;
	}


}
