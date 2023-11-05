package com.bbsk.banchanshop.user.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "USER")
public class UserEntity {
	
	@Id
	private String userId;
	
	@Column(nullable = false)
	private String userPw;
	
	@Column(nullable = false)
	private String userName;
	
	@Column(nullable = false, unique = true)
	private String userEmail;
	
	@Column(nullable = false)
	private String phoneNumber;
	
	@Column(nullable = false)
	private String address;
	
	@CreationTimestamp
	@Column(nullable = false)
	private LocalDateTime registDate;
	
	@Column(nullable = false)
	private boolean adminYn;
}
