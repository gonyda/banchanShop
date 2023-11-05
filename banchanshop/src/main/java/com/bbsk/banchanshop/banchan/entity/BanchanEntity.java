package com.bbsk.banchanshop.banchan.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name = "BANCHAN")
public class BanchanEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long banchanId;
	
	@Column(nullable = false)
	private int banchanQuantity;
	
	@Column(nullable = false)
	private String banchanName;
	
	@CreatedDate
	@Column(nullable = false)
	private LocalDateTime createDate;
	
	@CreatedDate
	@Column(nullable = false)
	private LocalDateTime expirationDate;
}
