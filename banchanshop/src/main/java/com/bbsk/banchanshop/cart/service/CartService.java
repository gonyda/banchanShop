package com.bbsk.banchanshop.cart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bbsk.banchanshop.banchan.entity.BanchanEntity;
import com.bbsk.banchanshop.cart.entity.CartEntity;
import com.bbsk.banchanshop.cart.entity.CartItemEntity;
import com.bbsk.banchanshop.cart.repository.CartItemRepository;
import com.bbsk.banchanshop.cart.repository.CartRepository;
import com.bbsk.banchanshop.user.entity.UserEntity;
import com.bbsk.banchanshop.user.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@Slf4j
public class CartService {

	private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepository;
	private final UserRepository userRepository;
	
	public CartService(final CartRepository cartRepository, final CartItemRepository cartItemRepository, final UserRepository userRepository) {
		this.cartRepository = cartRepository;
		this.cartItemRepository = cartItemRepository;
		this.userRepository = userRepository;
	}

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
		
		// 장바구니 아이템 저장
		// 장바구니 아이템에 중복 반찬이 있는지 체크
		CartItemEntity findCartItem = cartItemRepository.findByCartCartIdAndBanchanBanchanId(findUser.getCart().getCartId(), banchan.getBanchanId());
		
		if (findCartItem == null) {
			// 중복 반찬 없으면
			// cartItem 저장
			CartItemEntity saveCartItem = cartItemRepository.save(CartItemEntity.builder()
												.cart(findCart)
												.banchan(banchan)
												.banchanQuantity(itemQuantity)
												.banchanTotalPrice(itemQuantity * banchan.getBanchanPrice())
												.build());
			// cart 저장
			findCart.setCartItem(saveCartItem);
			findCart.updateTotalPice(saveCartItem.getBanchanTotalPrice() + findCart.getCartTotalPrice());
			findCart.updateTotalQuantity(saveCartItem.getBanchanQuantity() + findCart.getCartTotalQuantity());
			
			// user 저장
			findUser.setCart(findCart);
		} else {
			// 중복 반찬 있으면
			// cartItem update
			findCartItem.updateQuantity(itemQuantity + findCartItem.getBanchanQuantity());
			findCartItem.updateTotalPrice((itemQuantity * banchan.getBanchanPrice()) + findCartItem.getBanchanTotalPrice());
			
			// cart 저장
			findCart.setCartItem(findCartItem);
			findCart.updateTotalPice(findCartItem.getBanchanTotalPrice());
			findCart.updateTotalQuantity(findCartItem.getBanchanQuantity());
			// user 저장
			findUser.setCart(findCart);
		}
	}
}
