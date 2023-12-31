package com.bbsk.banchanshop.user.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bbsk.banchanshop.contant.UserType;
import com.bbsk.banchanshop.user.entity.UserEntity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Slf4j
public class UserServiceTests {
	
	@Autowired
	private UserService userService;

	@Transactional
	@DisplayName("회원가입 테스트")
	@Test
	public void registUser() {
		log.info("==================== 회원가입 테스트");

		UserEntity registEntity = userService.registUser(UserEntity.builder()
				.userId("test")
				.userPw("test")
				.userEmail("bbsk3939@gmil.com")
				.name("백승권")
				.address("서울특별실 양천구")
				.role(UserType.ROLE_USER)
				.phoneNumber("01064629657")
				.build());

		assertEquals("test", registEntity.getUserId());
		assertEquals("백승권", registEntity.getName());
		assertEquals(UserType.ROLE_USER, registEntity.getRole());
		assertEquals("test", registEntity.getCart().getCartId());
	}

}
