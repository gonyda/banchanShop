package com.bbsk.banchanshop.banchan.entity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
	@OneToMany(mappedBy = "banchan", fetch = FetchType.LAZY)
	private List<BanchanIngredientEntity> banchanIngredient;
	
	@Column(nullable = false)
	private int banchanQuantity;
	
	@Column(nullable = false, unique = true)
	private String banchanName;
	
	@Column(nullable = false)
	private int banchanPrice;

	@Column(nullable = false)
	private LocalDateTime createDate;
	
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
	 * 반찬 재료 등록
	 * @param list
	 */
	public void setIngredientList(List<BanchanIngredientEntity> list) {
		this.banchanIngredient = list;
	}
	
	/**
	 * 반찬 재고 수량 업데이트
	 * @param newQuantity
	 * @return 
	 */
	public BanchanEntity updateBanchanQuantity(int newQuantity) {
		this.banchanQuantity = newQuantity;
		return this;
	}
}
