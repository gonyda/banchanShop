package com.bbsk.banchanshop.banchan.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
	
	@ManyToOne
	@JoinColumn(name = "banchan_id")
	private BanchanEntity banchan;
	
	@Column(nullable = false)
	private String ingredientName;
	
	@Column(nullable = false)
	private int quantity;
	
	@Column(nullable = false)
	private LocalDateTime inputDate;
	
	@Column(nullable = false)
	private LocalDateTime expirationDate;

}
