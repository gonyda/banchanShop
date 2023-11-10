package com.bbsk.banchanshop.cart.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.bbsk.banchanshop.cart.entity.CartItemEntity;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.bbsk.banchanshop.banchan.entity.BanchanEntity;
import com.bbsk.banchanshop.banchan.entity.BanchanIngredientEntity;
import com.bbsk.banchanshop.banchan.service.BanchanService;
import com.bbsk.banchanshop.contant.UserType;
import com.bbsk.banchanshop.user.entity.UserEntity;
import com.bbsk.banchanshop.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class CartServiceTests {

	@Autowired
	private CartService cartService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private BanchanService banchanService;

	@Transactional
	@DisplayName("장바구니 상품 넣기 테스트")
	@Test
	public void putCart() {
		log.info(" =================== 장바구니 상품 넣기 테스트 ================== ");
		UserEntity user = saveUser();
		BanchanEntity banchan = saveFirstBanchan();

		cartService.addBanchanInCart(user, banchan, 2);

		UserEntity findUser = userService.findUserById(user.getUserId());

		assertEquals("test", findUser.getUserId()); // test 라는 유저가
		assertEquals("김치찌게", findUser.getCart().getCartItem().get(0).getBanchan().getBanchanName()); // 김치찌게를 장바구니에 담았다
		assertEquals(2, findUser.getCart().getCartItem().get(0).getBanchanQuantity()); // 2개를 담았고
		assertEquals(2, findUser.getCart().getCartTotalQuantity());// 장바구니 총 갯수는 2
		assertEquals(2*10000, findUser.getCart().getCartTotalPrice());// 장바구니 총 가격은 20000원이다

		// ======================================================================
		// ======================================================================
		log.info(" =================== 장바구니 같은 상품 넣기 테스트 ================== ");
		cartService.addBanchanInCart(findUser, banchan, 13);

		findUser = userService.findUserById(user.getUserId());

		assertEquals("test", findUser.getUserId()); // test 라는 유저가
		assertEquals("김치찌게", findUser.getCart().getCartItem().get(0).getBanchan().getBanchanName()); // 김치찌게를 장바구니에 담았다
		assertEquals(13, findUser.getCart().getCartItem().get(0).getBanchanQuantity()); // 13개를 담았고
		assertEquals(13, findUser.getCart().getCartTotalQuantity());// 장바구니 총 갯수는 13
		assertEquals(13*10000, findUser.getCart().getCartTotalPrice());// 장바구니 총 가격은 130000원


		// ======================================================================
		// ======================================================================
		log.info(" =================== 장바구니 다른 상품 넣기 테스트 ================== ");
		BanchanEntity 된장찌게 = saveSecondBanchan();

		cartService.addBanchanInCart(findUser, 된장찌게, 5);

		findUser = userService.findUserById(user.getUserId());

		assertEquals("test", findUser.getUserId()); // test 라는 유저가
		assertEquals("된장찌게", findUser.getCart().getCartItem().get(1).getBanchan().getBanchanName()); // 된장찌게를 장바구니에 담았다
		assertEquals(5, findUser.getCart().getCartItem().get(1).getBanchanQuantity()); // 5개를 담았고
		assertEquals(13+5, findUser.getCart().getCartTotalQuantity());// 장바구니 총 갯수는 13 + 5
		assertEquals(130000+25000, findUser.getCart().getCartTotalPrice());// 장바구니 총 가격은 120000 + 25000원
	}

	@Transactional
	@DisplayName("장바구니에 담겨있는 반찬 수량 변경")
	@Test
	public void updateQuantity() {
		log.info(" =================== 장바구니에 담겨있는 반찬 수량 변경 ================== ");
		UserEntity user = saveUser();
		BanchanEntity banchan = saveFirstBanchan();

		cartService.addBanchanInCart(user, banchan, 1);

		UserEntity findUser = userService.findUserById(user.getUserId());

		assertEquals(1, findUser.getCart().getCartItem().get(0).getBanchanQuantity());
		assertEquals(1*10000, findUser.getCart().getCartItem().get(0).getBanchanTotalPrice());
		assertEquals(1, findUser.getCart().getCartTotalQuantity());
		assertEquals(10000, findUser.getCart().getCartTotalPrice());

		log.info(" =================== 수량 1 -> 10 ================== ");
		// 수량 변경
		cartService.addBanchanInCart(user, banchan, 10);

		findUser = userService.findUserById(user.getUserId());

		assertEquals(10, findUser.getCart().getCartItem().get(0).getBanchanQuantity());
		assertEquals(10*10000, findUser.getCart().getCartItem().get(0).getBanchanTotalPrice());
		assertEquals(10, findUser.getCart().getCartTotalQuantity());
		assertEquals(100000, findUser.getCart().getCartTotalPrice());

	}

	@Transactional
	@DisplayName("장바구니 삭제 테스트")
	@Test
	public void delete() {
		UserEntity user = saveUser();

		// 첫번째 반찬
		cartService.addBanchanInCart(user, saveFirstBanchan(), 10);

		// 두번째 반찬
		cartService.addBanchanInCart(user, saveSecondBanchan(), 5);

		// 김치찌게 삭제
		cartService.deleteCartItem(user, user.getCart().getCartItem().get(0).getCartItemId());

		UserEntity findUser = userService.findUserById("test");

		assertEquals(5, findUser.getCart().getCartTotalQuantity());
		assertEquals(25000, findUser.getCart().getCartTotalPrice());
		assertEquals("된장찌게", cartService.findAllByCartId("test").get(0).getBanchan().getBanchanName());
	}

	@DisplayName("반찬 재고 수량 체크 테스트")
	@Test
	public void checkStockQuantity() {
		UserEntity user = saveUser();
		BanchanEntity banchan = saveFirstBanchan();

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			cartService.addBanchanInCart(user, banchan, 20);
		});
		log.info(exception.getMessage());
	}

	@Transactional
	@DisplayName("유저 장바구니 전체 조회 테스트")
	@Test
	public void findAll() {
		UserEntity user = saveUser();
		BanchanEntity banchan = saveFirstBanchan();

		cartService.addBanchanInCart(user, banchan, 2);

		List<CartItemEntity> cartItems = cartService.findAllByCartId(user.getUserId());

		assertEquals(1, cartItems.size());
	}

	private UserEntity saveUser(){
		return userService.registUser(UserEntity.builder()
				.userId("test")
				.userPw("test")
				.userEmail("bbsk3939@gmil.com")
				.userName("백승권")
				.address("서울특별실 양천구")
				.adminYn(UserType.N)
				.phoneNumber("01064629657")
				.build());
	}

	private BanchanEntity saveFirstBanchan() {
		BanchanEntity entity = BanchanEntity.builder()
				.banchanStockQuantity(15)
				.banchanName("김치찌게")
				.banchanPrice(10000)
				.createDate(LocalDateTime.now())
				.build();
		entity.plusExpirationDate(3L);

		// ====================================================================
		List<BanchanIngredientEntity> list = new ArrayList<>();
		BanchanIngredientEntity 고춧가루 = BanchanIngredientEntity.builder()
				.ingredientName("고춧가루")
				.quantity(5)
				.inputDate(LocalDateTime.now())
				.build();
		고춧가루.plusExpirationDate(10L);

		BanchanIngredientEntity 간장 = BanchanIngredientEntity.builder()
				.ingredientName("간장")
				.quantity(5)
				.inputDate(LocalDateTime.now())
				.build();
		간장.plusExpirationDate(20L);

		BanchanIngredientEntity 소금 = BanchanIngredientEntity.builder()
				.ingredientName("소금")
				.quantity(5)
				.inputDate(LocalDateTime.now())
				.build();
		소금.plusExpirationDate(20L);

		list.add(고춧가루);
		list.add(간장);
		list.add(소금);

		return banchanService.registBanchan(entity, list);
	}

	private BanchanEntity saveSecondBanchan() {
		BanchanEntity entity = BanchanEntity.builder()
				.banchanStockQuantity(15)
				.banchanName("된장찌게")
				.banchanPrice(5000)
				.createDate(LocalDateTime.now())
				.build();
		entity.plusExpirationDate(3L);

		// ====================================================================
		List<BanchanIngredientEntity> list = new ArrayList<>();
		BanchanIngredientEntity 된장 = BanchanIngredientEntity.builder()
				.ingredientName("된장")
				.quantity(5)
				.inputDate(LocalDateTime.now())
				.build();
		된장.plusExpirationDate(10L);

		BanchanIngredientEntity 양파 = BanchanIngredientEntity.builder()
				.ingredientName("양파")
				.quantity(5)
				.inputDate(LocalDateTime.now())
				.build();
		양파.plusExpirationDate(20L);

		list.add(된장);
		list.add(양파);

		return banchanService.registBanchan(entity, list);
	}
}
