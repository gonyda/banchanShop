package com.bbsk.banchanshop.user.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import com.bbsk.banchanshop.security.serivce.Sha512CustomPasswordEncoder;
import com.bbsk.banchanshop.user.dto.RequestUserDto;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.bbsk.banchanshop.cart.entity.CartEntity;
import com.bbsk.banchanshop.contant.UserType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@ToString
@DynamicInsert
@DynamicUpdate
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "USER")
public class UserEntity implements UserDetails {
	
	@Id
	private String userId;

	@ToString.Exclude
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cart_id", nullable = false)
	private CartEntity cart;
	
	@Column(nullable = false)
	private String userPw;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false, unique = true)
	private String userEmail;
	
	@Column(nullable = false, unique = true)
	private String phoneNumber;
	
	@Column(nullable = false)
	private String address;
	
	@CreationTimestamp
	@Column(nullable = false)
	private LocalDateTime registDate;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private UserType role;
	
	public void changePw(String pw) {
		this.userPw = new Sha512CustomPasswordEncoder().encode(pw);
	}

	public void setCart(CartEntity findCart) {
		this.cart = findCart;
	}

	/**
	 * 유저정보 수정
	 * @param requestUserDto
	 */
	public void updateUser(RequestUserDto requestUserDto) {
		if(requestUserDto.getUserPw() != null && !requestUserDto.getUserPw().isEmpty()) {
			if(requestUserDto.getUserPw().equals(requestUserDto.getConfirmPassword())) {
				changePw(requestUserDto.getUserPw());
			}
		}
		this.name = requestUserDto.getName();
		this.userEmail = requestUserDto.getUserEmail();
		this.phoneNumber = requestUserDto.getPhoneNumber();
		this.address = requestUserDto.getAddress();

	}

	/* ============================ security ============================ */

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> roles = new ArrayList<>();

		for(String role : this.role.toString().split(",")){
			roles.add(new SimpleGrantedAuthority(role));
		}
		return roles;
	}

	@Override
	public String getPassword() {
		return userPw;
	}

	@Override
	public String getUsername() {
		return userId;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public void updateRole(UserType role) {
		this.role = role;
	}
}
