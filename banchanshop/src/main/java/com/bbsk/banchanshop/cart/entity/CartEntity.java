package com.bbsk.banchanshop.cart.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@DynamicUpdate
@DynamicInsert
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "CART")
public class CartEntity {
	
	@Id
	private String cartId;
	
	@ToString.Exclude
	@OneToMany(mappedBy = "cart")
	private List<CartItemEntity> cartItem = new ArrayList<>();
	
	@ColumnDefault("0")
	@Column(nullable = false)
	private int cartTotalPrice;
	
	@Column(nullable = false)
	@ColumnDefault("0")
	private int cartTotalQuantity;

	public void updateCartItem(List<CartItemEntity> cartItem) {
		this.cartItem = cartItem;
	}

	public void newCartOrUpdateCart(CartItemEntity newCartItem) {
		int isExistIndex = -1;
		for (int i = 0; i < this.cartItem.size(); i++) {
			if(this.cartItem.get(i).getBanchan().getBanchanId() == newCartItem.getCartItemId()) {
				isExistIndex = i;
				break;
			}
		}

		if(isExistIndex != -1) {
			this.cartItem.set(isExistIndex, newCartItem);
		} else {
			this.cartItem.add(newCartItem);
		}
	}

	public void updateTotalPiceAndTotalQuantity(int price, int quantity) {
		this.cartTotalPrice = price;
		this.cartTotalQuantity = quantity;
	}
}
