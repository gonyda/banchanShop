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

import java.util.List;

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
		CartEntity findCart = cartRepository.findById(user.getCart().getCartId()).orElse(null);
		CartItemEntity findCartItem = cartItemRepository.findByCartCartIdAndBanchanBanchanId(user.getCart().getCartId(), banchan.getBanchanId());

		if (checkStockQuantity(banchan, itemQuantity)) {
			throw new IllegalArgumentException("재고수량보다 더 많은 수량을 선택할 수 없습니다.");
		}

		// 1. cart_item 저장 / 수정
		findCart.setCartItem((findCartItem == null) ?
							newCartItem(banchan, itemQuantity, findCart) :
							updateCartItem(banchan, itemQuantity, findCartItem, findCart));
		// 2. cart 총합 및 총갯수 수정
		findCart.updateTotalPiceAndTotalQuantity(getSumPrice(findCart),
												getSumQuantity(findCart));
	}

	/**
	 * 장바구니 반찬 삭제
	 * cartItem - delete, cart - 총액,총갯수 update
	 * @param userEntity
	 * @param cartItemId
	 */
	@Transactional
	public void deleteCartItem(UserEntity userEntity, Long cartItemId) {
		CartEntity findCart = cartRepository.findById(userEntity.getCart().getCartId()).orElse(null);
		CartItemEntity findCartItem = cartItemRepository.findById(cartItemId).orElse(null);

		cartItemRepository.deleteById(cartItemId);
		// cart 총합 및 총갯수 수정
		findCart.updateTotalPiceAndTotalQuantity(getSubstrctedPirce(findCart, findCartItem),
												getSubstrctedQuantity(findCart, findCartItem));
	}

	/**
	 * 해당 유저의 장바구니 전체조회
	 * @param cartId
	 * @return
	 */
	public List<CartItemEntity> findAllByCartId(String cartId) {
		return cartItemRepository.findAllByCartCartId(cartId);
	}

	/**
	 * 기존 장바구니 아이템 update, 수량 및 총합 변경
	 * @param banchan
	 * @param itemQuantity
	 * @param findCartItem
	 * @param findCart
	 * @return
	 */
	private static CartItemEntity updateCartItem(BanchanEntity banchan, int itemQuantity, CartItemEntity findCartItem, CartEntity findCart) {
		return findCartItem.updateCartItem(findCart, banchan, itemQuantity);
	}

	/**
	 * 장바구니 아이템 신규 저장
	 * @param banchan
	 * @param itemQuantity
	 * @param findCart
	 * @return
	 */
	private CartItemEntity newCartItem(BanchanEntity banchan, int itemQuantity, CartEntity findCart) {
		return cartItemRepository.save(CartItemEntity.builder()
				.banchan(banchan)
				.cart(findCart)
				.banchanQuantity(itemQuantity)
				.banchanTotalPrice(itemQuantity * banchan.getBanchanPrice())
				.build());
	}

	/**
	 * 장바구니에 삼풍 담을 시 반찬재고 수량 체크
	 * @param banchan
	 * @param itemQuantity
	 * @return
	 */
	private boolean checkStockQuantity(BanchanEntity banchan, int itemQuantity) {
		return banchan.getBanchanStockQuantity() < itemQuantity;
	}

	/**
	 * 카트에 담긴 모든 반찬 총 갯수
	 * @param findCart
	 * @return
	 */
	private static int getSumQuantity(CartEntity findCart) {
		return findCart.getCartItem().stream().mapToInt(CartItemEntity::getBanchanQuantity).sum();
	}

	/**
	 * 카트에 담긴 모든 반찬 총합
	 * @param findCart
	 * @return
	 */
	private static int getSumPrice(CartEntity findCart) {
		return findCart.getCartItem().stream().mapToInt(CartItemEntity::getBanchanTotalPrice).sum();
	}

	/**
	 * 장바구니에 반찬 삭제 시, 삭제한 반찬 갯수 만큼 장바구니에 담긴 총 갯수 업데이트
	 * @param findCart
	 * @param findCartItem
	 * @return
	 */
	private int getSubstrctedQuantity(CartEntity findCart, CartItemEntity findCartItem) {
		return findCart.getCartTotalQuantity() - findCartItem.getBanchanQuantity();
	}

	/**
	 * 장바구니에 반찬 삭제 시, 삭제한 반찬 총합 만큼 장바구니에 담긴 총합 업데이트
	 * @param findCart
	 * @param findCartItem
	 * @return
	 */
	private int getSubstrctedPirce(CartEntity findCart, CartItemEntity findCartItem) {
		return findCart.getCartTotalPrice() - findCartItem.getBanchanTotalPrice();
	}
}
