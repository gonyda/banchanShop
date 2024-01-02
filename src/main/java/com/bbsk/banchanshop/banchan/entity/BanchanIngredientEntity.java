package com.bbsk.banchanshop.banchan.entity;

import java.time.Duration;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "BANCHAN_INGREDIENT")
public class BanchanIngredientEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long BanchanIngredientId;
	
	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "banchan_id", nullable = false)
	private BanchanEntity banchan;
	
	@Column(nullable = false)
	private String ingredientName;
	
	@Column(nullable = false)
	private int quantity;
	
	@Column(nullable = false)
	private LocalDateTime inputDate;
	
	@Column(nullable = false)
	private LocalDateTime expirationDate;

	public void plusExpirationDate(Long plusDay) {
		this.expirationDate = LocalDateTime.now().plusDays(Duration.ofDays(plusDay).toDays());
	}
	
	public void setBanchan(BanchanEntity banchan) {
		this.banchan = banchan;
	}

	public BanchanIngredientEntity updateIngredientQuantity(int newQuantity) {
		this.quantity = newQuantity;
		return this;
	}
}
