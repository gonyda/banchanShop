package com.bbsk.banchanshop.cart.entity;

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
	private List<CartItemEntity> cartItem;
	
	@ColumnDefault("0")
	@Column(nullable = false)
	private int cartTotalPrice;
	
	@Column(nullable = false)
	@ColumnDefault("0")
	private int cartTotalQuantity;

	public void setCartItem(CartItemEntity findCartItem) {
		int isExistIndex = -1;
		for (int i = 0; i < cartItem.size(); i++) {
			if(cartItem.get(i).getBanchan().getBanchanId() == findCartItem.getCartItemId()) {
				isExistIndex = i;
				break;
			}
		}
		
		if(isExistIndex != -1) {
			cartItem.set(isExistIndex, findCartItem);
		} else {
			cartItem.add(findCartItem);
		}
	}
	
	public void updateTotalPice(int price) {
		this.cartTotalPrice = price;
	}
	
	public void updateTotalQuantity(int quantity) {
		this.cartTotalQuantity = quantity;
	}
}
