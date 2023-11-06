package com.bbsk.banchanshop.user.dto;

import java.time.LocalDateTime;

import com.bbsk.banchanshop.contant.UserType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegistUserDto {

	private String userId;
	
	private String userPw;
	
	private String userName;
	
	private String userEmail;
	
	private String phoneNumber;
	
	private String address;
	
	private LocalDateTime registDate;
	
	private UserType adminYn;
}
