package com.bbsk.banchanshop.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bbsk.banchanshop.cart.entity.CartEntity;
import com.bbsk.banchanshop.cart.repository.CartRepository;
import com.bbsk.banchanshop.user.dto.RegistUserDto;
import com.bbsk.banchanshop.user.entity.UserEntity;
import com.bbsk.banchanshop.user.repository.UserRepository;

@Service
public class UserService {
	
	private final UserRepository userRepo;
	private final CartRepository cartRepo;

	public UserService(final UserRepository userRepo, final CartRepository cartRepo) {
		this.userRepo = userRepo;
		this.cartRepo = cartRepo;
	}

	@Transactional
	public UserEntity registUser(RegistUserDto dto) {
		return userRepo.save(UserEntity.builder()
										.userId(dto.getUserId())
										.userPw(dto.getUserPw())
										.userEmail(dto.getUserEmail())
										.userName(dto.getUserName())
										.address(dto.getAddress())
										.adminYn(dto.getAdminYn())
										.cart(cartRepo.save(CartEntity.builder().cartId(dto.getUserId()).build()))
										.phoneNumber(dto.getPhoneNumber())
										.build());
	}
}
