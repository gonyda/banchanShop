package com.bbsk.banchanshop.banchan.entity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
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
	
	@ToString.Exclude
	@OneToMany(mappedBy = "banchan")
	@Column(nullable = false)
	private List<BanchanIngredientEntity> banchanIngredient = new ArrayList<>();
	
	@Column(nullable = false)
	private int banchanStockQuantity;
	
	@Column(nullable = false, unique = true)
	private String banchanName;
	
	@Column(nullable = false)
	private int banchanPrice;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	@Column(nullable = false)
	private LocalDateTime createDate;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	@Column(nullable = false)
	private LocalDateTime expirationDate;
	
	/**
	 * 반찬 유통기한
	 * @param plusDay
	 */
	public void plusExpirationDate(Long plusDay) {
		this.expirationDate = LocalDateTime.now().plusDays(Duration.ofDays(plusDay).toDays());
	}
	
	/**
	 * 반찬 재고 수량 업데이트
	 * @param newQuantity
	 * @return 
	 */
	public BanchanEntity updateBanchanQuantity(int newQuantity) {
		this.banchanStockQuantity = newQuantity;
		return this;
	}

	/**
	 * 반찬 이름 변경
	 * @param newName
	 */
	public void updateName(String newName) {
		this.banchanName = newName;
	}

	/**
	 * 반찬 가격 변경
	 * @param newPrice
	 */
	public void updateBanchanPrice(int newPrice) {
		this.banchanPrice = newPrice;
	}
}
