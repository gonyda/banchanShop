package com.bbsk.banchanshop.cart.service;

import com.bbsk.banchanshop.cart.dto.ResponseUserCartDto;
import com.bbsk.banchanshop.cart.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bbsk.banchanshop.banchan.entity.BanchanEntity;
import com.bbsk.banchanshop.cart.entity.CartEntity;
import com.bbsk.banchanshop.cart.entity.CartItemEntity;
import com.bbsk.banchanshop.cart.repository.CartRepository;
import com.bbsk.banchanshop.user.entity.UserEntity;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CartService {

	private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepository;

	/**
	 * 장바구니에 반찬 담기 및 수량 수정
	 * @param user 장바구니 주인
	 * @param banchan 담을 반찬
	 * @param itemQuantity 담을 반찬 갯수
	 */
	@Transactional
	public void addBanchanInCart(UserEntity user, BanchanEntity banchan, int itemQuantity) {
		CartEntity cart = cartRepository.findById(user.getCart().getCartId()).orElse(null);
		cart.updateCartItem(cartItemRepository.findAllByCartCartId(cart.getCartId()));
		CartItemEntity existingCartItem = existingCartItem(user, banchan); // 장바구니에 존재하는 반찬인지

		if (checkStockQuantity(banchan, itemQuantity)) {
			throw new IllegalArgumentException("재고수량보다 더 많은 수량을 선택할 수 없습니다.");
		}

		// 1. cart_item 저장 / 수정
		cart.newCartOrUpdateCart((existingCartItem == null) ? newCartItem(banchan, itemQuantity, cart) :
				 											  existingCartItem.updateCartItem(cart, banchan, itemQuantity));

		// 2. cart 총합 및 총갯수 수정
		cart.updateTotalPiceAndTotalQuantity(getSumPrice(cart),
												getSumQuantity(cart));
	}

	/**
	 * 장바구니 반찬 삭제
	 * cartItem - delete, cart - 총액,총갯수 update
	 * @param userEntity
	 * @param cartItemId
	 */
	@Transactional
	public void deleteCartItem(UserEntity userEntity, Long cartItemId) {
		CartEntity findCart = userEntity.getCart();
		CartItemEntity findCartItem = cartItemRepository.findById(cartItemId).orElse(null);

		cartItemRepository.deleteById(cartItemId);
		// cart 총합 및 총갯수 수정
		findCart.updateTotalPiceAndTotalQuantity(getSubstrctedPirce(findCart, findCartItem),
												getSubstrctedQuantity(findCart, findCartItem));
	}

	/**
	 * 해당 유저의 장바구니 조회
	 * @param cartId
	 * @return
	 */
	public CartEntity findByCartId(String cartId) {
		return cartRepository.findById(cartId).orElse(null);
	}

	/**
	 * 해당 유저의 장바구니 아이템 전체조회
	 * @param cartId
	 * @return
	 */
	public List<CartItemEntity> findAllByCartId(String cartId) {
		return cartItemRepository.findAllByCartCartId(cartId);
	}

	private CartItemEntity existingCartItem(UserEntity user, BanchanEntity banchan) {
		return cartItemRepository.findByCartCartIdAndBanchanBanchanId(user.getCart().getCartId(), banchan.getBanchanId());
	}

	/**
	 * 장바구니 아이템 신규 저장
	 * @param banchan
	 * @param itemQuantity
	 * @param cart
	 * @return
	 */
	private CartItemEntity newCartItem(BanchanEntity banchan, int itemQuantity, CartEntity cart) {
		return cartItemRepository.save(CartItemEntity.builder()
				.banchan(banchan)
				.cart(cart)
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
	private int getSumQuantity(CartEntity findCart) {
		return findCart.getCartItem().stream().mapToInt(CartItemEntity::getBanchanQuantity).sum();
	}

	/**
	 * 카트에 담긴 모든 반찬 총합
	 * @param findCart
	 * @return
	 */
	private int getSumPrice(CartEntity findCart) {
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
