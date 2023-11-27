package com.bbsk.banchanshop.user.service;

import com.bbsk.banchanshop.security.serivce.Sha512CustomPasswordEncoder;
import com.bbsk.banchanshop.user.dto.RequestUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bbsk.banchanshop.cart.entity.CartEntity;
import com.bbsk.banchanshop.cart.repository.CartRepository;
import com.bbsk.banchanshop.user.entity.UserEntity;
import com.bbsk.banchanshop.user.repository.UserRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepo;
	private final CartRepository cartRepo;

	/**
	 * 회원가입
	 * @param user
	 * @return
	 */
	@Transactional
	public UserEntity registUser(UserEntity user) {

		return userRepo.save(UserEntity.builder()
										.userId(user.getUserId())
										.userPw(new Sha512CustomPasswordEncoder().encode(user.getUserPw()))
										.userEmail(user.getUserEmail())
										.name(user.getName())
										.address(user.getAddress())
										.adminYn(user.getAdminYn())
										.cart(cartRepo.save(CartEntity.builder().cartId(user.getUserId()).build()))
										.phoneNumber(user.getPhoneNumber())
										.build());
	}

	/**
	 * 유저 조회
	 * @param userId
	 * @return
	 */
	public UserEntity findUserById(String userId) {
		return userRepo.findById(userId).orElse(null);
	}

	@Transactional
	public void updateUser(RequestUserDto requestUserDto) {
		UserEntity user = userRepo.findById(requestUserDto.getUserId()).orElseThrow(() -> new IllegalArgumentException("아이디를 확인해주세요"));

		user.updateUser(requestUserDto);
	}

	//TODO 유저 전체조회 (운영자)
}
