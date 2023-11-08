package com.bbsk.banchanshop.cart.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class CartService {

	private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepository;
	private final UserRepository userRepository;
	
	/**
	 * 장바구니에 반찬 담기 및 수량 수정
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
		CartItemEntity saveCartItem = null;
		if (findCartItem == null) {
			findCartItem = cartItemRepository.save(CartItemEntity.builder()
					.banchan(banchan)
					.cart(findCart)
					.banchanQuantity(itemQuantity)
					.banchanTotalPrice(itemQuantity * banchan.getBanchanPrice())
					.build());
		} else {
			findCartItem = findCartItem.updateCartItem(findCart, banchan, itemQuantity);
		}

		// cart 저장
		findCart.setCartItem(findCartItem);
		findCart.updateTotalPiceAndTotalQuantity(getSumPrice(findCart), getSumQuantity(findCart));

		// user 저장
		findUser.setCart(findCart);
	}

	/**
	 * 장바구니 반찬 삭제
	 * cartItem - delete, cart - 총액,총갯수 update
	 * @param userEntity
	 * @param cartItemId
	 */
	@Transactional
	public void deleteCartItem(UserEntity userEntity, Long cartItemId) {
		UserEntity findUser = userRepository.findById(userEntity.getUserId()).orElse(null);
		CartEntity findCart = cartRepository.findById(userEntity.getCart().getCartId()).orElse(null);
		CartItemEntity findCartItem = cartItemRepository.findById(cartItemId).orElse(null);

		cartItemRepository.deleteById(cartItemId);
		findCart.updateTotalPiceAndTotalQuantity(getSubstrctedPirce(findCart, findCartItem), getSubstrctedQuantity(findCart, findCartItem));
	}

	private static int getSumQuantity(CartEntity findCart) {
		return findCart.getCartItem().stream().mapToInt(e -> e.getBanchanQuantity()).sum();
	}

	private static int getSumPrice(CartEntity findCart) {
		return findCart.getCartItem().stream().mapToInt(e -> e.getBanchanTotalPrice()).sum();
	}

	private int getSubstrctedQuantity(CartEntity findCart, CartItemEntity findCartItem) {
		return findCart.getCartTotalQuantity() - findCartItem.getBanchanQuantity();
	}

	private int getSubstrctedPirce(CartEntity findCart, CartItemEntity findCartItem) {
		return findCart.getCartTotalPrice() - findCartItem.getBanchanTotalPrice();
	}
}
