package com.bbsk.banchanshop.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bbsk.banchanshop.contant.UserType;
import com.bbsk.banchanshop.user.entity.UserEntity;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class UserServiceTests {
	
	@Autowired
	private UserService userService;
	
	@DisplayName("회원가입 테스트")
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
		
		log.info("user.toString() : {}", registEntity.toString());
		assertEquals("test", registEntity.getUserId());
		assertEquals("백승권", registEntity.getUserName());
		assertEquals(UserType.N, registEntity.getAdminYn());
		assertEquals("test", registEntity.getCart().getCartId());
	}

}
