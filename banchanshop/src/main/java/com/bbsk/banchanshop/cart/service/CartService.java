package com.bbsk.banchanshop.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bbsk.banchanshop.banchan.entity.BanchanEntity;
import com.bbsk.banchanshop.cart.entity.CartEntity;
import com.bbsk.banchanshop.cart.entity.CartItemEntity;
import com.bbsk.banchanshop.cart.repository.CartItemRepository;
import com.bbsk.banchanshop.cart.repository.CartRepository;
import com.bbsk.banchanshop.user.entity.UserEntity;
import com.bbsk.banchanshop.user.repository.UserRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartService {

	private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepository;
	private final UserRepository userRepository;
	
	/**
	 * 장바구니에 반찬 넣기
	 * @param user 장바구니 주인
	 * @param banchan 담을 반찬
	 * @param itemQuantity 담을 반찬 갯수
	 */
	@Transactional
	public void addBanchanInCart(UserEntity user, BanchanEntity banchan, int itemQuantity) {
		UserEntity findUser = userRepository.findById(user.getUserId()).orElse(null);
		CartEntity findCart = cartRepository.findById(findUser.getCart().getCartId()).orElse(null);
		CartItemEntity findCartItem = cartItemRepository.findByCartCartIdAndBanchanBanchanId(findUser.getCart().getCartId(), banchan.getBanchanId());
		
		// 장바구니 아이템 저장
		// 장바구니 아이템에 중복 반찬이 있는지 체크
		if (findCartItem == null) {
			// 중복 반찬 없으면
			// cartItem 저장
			CartItemEntity saveCartItem = getSaveCartItem(banchan, itemQuantity, findCart);
			// cart 저장
			saveCart(findCart, saveCartItem, true);
		} else {
			// 중복 반찬 있으면
			// cartItem update
			updateCartItem(banchan, itemQuantity, findCartItem);

			// cart 저장
			saveCart(findCart, findCartItem, false);
		}

		// user 저장
		findUser.setCart(findCart);
	}

	private void updateCartItem(BanchanEntity banchan, int itemQuantity, CartItemEntity findCartItem) {
		findCartItem.updateQuantity(itemQuantity + findCartItem.getBanchanQuantity());
		findCartItem.updateTotalPrice((itemQuantity * banchan.getBanchanPrice()) + findCartItem.getBanchanTotalPrice());
	}

	private CartItemEntity getSaveCartItem(BanchanEntity banchan, int itemQuantity, CartEntity findCart) {
		return cartItemRepository.save(CartItemEntity.builder()
				.cart(findCart)
				.banchan(banchan)
				.banchanQuantity(itemQuantity)
				.banchanTotalPrice(itemQuantity * banchan.getBanchanPrice())
				.build());
	}

	private void saveCart(CartEntity cart, CartItemEntity cartItem, boolean isExist) {
		if (isExist) {
			cart.setCartItem(cartItem);
			cart.updateTotalPice(cartItem.getBanchanTotalPrice() + cart.getCartTotalPrice());
			cart.updateTotalQuantity(cartItem.getBanchanQuantity() + cart.getCartTotalQuantity());
		} else {
			cart.setCartItem(cartItem);
			cart.updateTotalPice(cartItem.getBanchanTotalPrice());
			cart.updateTotalQuantity(cartItem.getBanchanQuantity());
		}
	}
}
