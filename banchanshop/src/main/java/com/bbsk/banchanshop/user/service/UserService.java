package com.bbsk.banchanshop.user.service;

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
										.userPw(user.getUserPw())
										.userEmail(user.getUserEmail())
										.userName(user.getUserName())
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
}
