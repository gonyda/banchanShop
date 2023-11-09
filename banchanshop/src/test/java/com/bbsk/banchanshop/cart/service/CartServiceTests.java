package com.bbsk.banchanshop.cart.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.after;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.bbsk.banchanshop.cart.entity.CartItemEntity;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.bbsk.banchanshop.banchan.entity.BanchanEntity;
import com.bbsk.banchanshop.banchan.entity.BanchanIngredientEntity;
import com.bbsk.banchanshop.banchan.service.BanchanService;
import com.bbsk.banchanshop.contant.UserType;
import com.bbsk.banchanshop.user.entity.UserEntity;
import com.bbsk.banchanshop.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@Slf4j
@Transactional
@Rollback(false)
public class CartServiceTests {

	@Autowired
	private CartService cartService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BanchanService banchanService;
	
	@DisplayName("회원가입 테스트")
	@Order(1)
	@Test
	public void registUser() {
		log.info("==================== 회원가입 테스트");
		
		UserEntity user = UserEntity.builder()
				.userId("test")
				.userPw("test")
				.userEmail("bbsk3939@gmail.com")
				.userName("백승권")
				.address("서울특별실 양천구")
				.adminYn(UserType.N)
				.phoneNumber("01064629667")
				.build();
		
		UserEntity registEntity = userService.registUser(user);
		
		assertEquals("test", registEntity.getUserId());
		assertEquals("백승권", registEntity.getUserName());
		assertEquals(UserType.N, registEntity.getAdminYn());
		assertEquals("test", registEntity.getCart().getCartId());
	}
	
	@Order(2)
	@DisplayName("반찬등록 테스트")
	@Test
	public void insertBanchan() {
		log.info("=================== 반찬등록 테스트 =================");
		BanchanEntity entity = BanchanEntity.builder()
							 .banchanStockQuantity(100)
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
		
		
		BanchanEntity firstBanchan = banchanService.registBanchan(entity, list);
		
		// ====================================================================
		// ====================================================================
		// 된장찌게 반찬 추가
		
		BanchanEntity entity1 = BanchanEntity.builder()
				 .banchanStockQuantity(100)
				 .banchanName("된장찌게")
				 .banchanPrice(5000)
				 .createDate(LocalDateTime.now())
				 .build();
		entity1.plusExpirationDate(3L);
		
		// ====================================================================
		List<BanchanIngredientEntity> list1 = new ArrayList<>();
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
		
		list1.add(된장);
		list1.add(양파);
		
		
		BanchanEntity secondBanchan = banchanService.registBanchan(entity1, list1);
		// ====================================================================
		
		assertEquals("김치찌게", firstBanchan.getBanchanName());
		assertEquals("된장찌게", secondBanchan.getBanchanName());
		
	}
	
	@Order(3)
	@DisplayName("장바구니 상품 넣기 테스트")
	@Test
	public void putCart() {
		log.info(" =================== 장바구니 상품 넣기 테스트 ================== ");
		UserEntity userEntity = userService.findUserById("test");
		BanchanEntity banchanEntity = banchanService.findBybanchanName("김치찌게");
		
		cartService.addBanchanInCart(userEntity, banchanEntity, 2);
		
		UserEntity afterUserEntity = userService.findUserById("test");
		assertEquals("test", afterUserEntity.getUserId()); // test 라는 유저가
		assertEquals("김치찌게", afterUserEntity.getCart().getCartItem().get(0).getBanchan().getBanchanName()); // 김치찌게를 장바구니에 담았다
		assertEquals(2, afterUserEntity.getCart().getCartItem().get(0).getBanchanQuantity()); // 2개를 담았고
		assertEquals(2, afterUserEntity.getCart().getCartTotalQuantity());// 장바구니 총 갯수는 2
		assertEquals(2*10000, afterUserEntity.getCart().getCartTotalPrice());// 장바구니 총 가격은 20000원이다
		
		// ======================================================================
		// ======================================================================
		log.info(" =================== 장바구니 같은 상품 넣기 테스트 ================== ");
		
		cartService.addBanchanInCart(userEntity, banchanEntity, 12);
		
		afterUserEntity = userService.findUserById("test");
		
		assertEquals("test", afterUserEntity.getUserId()); // test 라는 유저가
		assertEquals("김치찌게", afterUserEntity.getCart().getCartItem().get(0).getBanchan().getBanchanName()); // 김치찌게를 장바구니에 담았다
		assertEquals(12, afterUserEntity.getCart().getCartItem().get(0).getBanchanQuantity()); // 12개를 담았고
		assertEquals(12, afterUserEntity.getCart().getCartTotalQuantity());// 장바구니 총 갯수는 12
		assertEquals(12*10000, afterUserEntity.getCart().getCartTotalPrice());// 장바구니 총 가격은 120000원
		
		
		// ======================================================================
		// ======================================================================
		log.info(" =================== 장바구니 다른 상품 넣기 테스트 ================== ");
		
		BanchanEntity 된장찌게 = banchanService.findBybanchanName("된장찌게");
		
		cartService.addBanchanInCart(userEntity, 된장찌게, 5);
		afterUserEntity = userService.findUserById("test");

		assertEquals("test", afterUserEntity.getUserId()); // test 라는 유저가
		assertEquals("된장찌게", afterUserEntity.getCart().getCartItem().get(1).getBanchan().getBanchanName()); // 된장찌게를 장바구니에 담았다
		assertEquals(5, afterUserEntity.getCart().getCartItem().get(1).getBanchanQuantity()); // 5개를 담았고
		assertEquals(12+5, afterUserEntity.getCart().getCartTotalQuantity());// 장바구니 총 갯수는 12 + 5
		assertEquals(120000+25000, afterUserEntity.getCart().getCartTotalPrice());// 장바구니 총 가격은 120000 + 25000원
	}

	@Order(4)
	@DisplayName("장바구니에 담겨있는 반찬 수량 변경")
	@Test
	public void updateQuantity() {
		UserEntity userEntity = userService.findUserById("test");
		BanchanEntity banchanEntity = banchanService.findBybanchanName("김치찌게");

		cartService.addBanchanInCart(userEntity, banchanEntity, 1);

		UserEntity afterUserEntity = userService.findUserById("test");

		assertEquals(1, userEntity.getCart().getCartItem().get(0).getBanchanQuantity());
		assertEquals(1*10000, userEntity.getCart().getCartItem().get(0).getBanchanTotalPrice());
		assertEquals(1+5, afterUserEntity.getCart().getCartTotalQuantity());
		assertEquals(10000+25000, afterUserEntity.getCart().getCartTotalPrice());
	}

	/**
	 * 테스트 완료
	 */
	@Disabled
	@Order(5)
	@DisplayName("장바구니 삭제 테스트")
	@Test
	public void delete() {
		UserEntity userEntity = userService.findUserById("test");

		cartService.deleteCartItem(userEntity, 2L);

		UserEntity afterUserEntity = userService.findUserById("test");

		assertEquals(1, userEntity.getCart().getCartTotalQuantity());
		assertEquals(10000, userEntity.getCart().getCartTotalPrice());
		assertEquals("김치찌게", userEntity.getCart().getCartItem().get(0).getBanchan().getBanchanName());
	}

	/*
	 * 테스트 성공
	 * */
	@Disabled
	@Order(6)
	@DisplayName("반찬 재고 수량 체크 테스트")
	@Test
	public void checkStockQuantity() {
		// ====================================================================
		// ====================================================================
		// 콩나물무침 추가
		BanchanEntity entity1 = BanchanEntity.builder()
				.banchanStockQuantity(1)
				.banchanName("콩나물무침")
				.banchanPrice(1000)
				.createDate(LocalDateTime.now())
				.build();
		entity1.plusExpirationDate(3L);

		// ====================================================================
		List<BanchanIngredientEntity> list1 = new ArrayList<>();
		BanchanIngredientEntity 콩나물 = BanchanIngredientEntity.builder()
				.ingredientName("콩나물")
				.quantity(5)
				.inputDate(LocalDateTime.now())
				.build();
		콩나물.plusExpirationDate(10L);

		BanchanIngredientEntity 참기름 = BanchanIngredientEntity.builder()
				.ingredientName("참기름")
				.quantity(5)
				.inputDate(LocalDateTime.now())
				.build();
		참기름.plusExpirationDate(20L);

		list1.add(콩나물);
		list1.add(참기름);

		banchanService.registBanchan(entity1, list1);

		// =====================================
		UserEntity user = userService.findUserById("test");
		BanchanEntity banchan = banchanService.findBybanchanName("콩나물무침");

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			cartService.addBanchanInCart(user, banchan, 2);
		});
		log.info(exception.getMessage());

	}

	@Order(7)
	@DisplayName("유저 장바구니 전체 조회 테스트")
	@Test
	public void findAll() {
		UserEntity user = userService.findUserById("test");

		List<CartItemEntity> cartItems = cartService.findAllByCartId(user.getUserId());

		assertEquals(2, cartItems.size());
	}
}
