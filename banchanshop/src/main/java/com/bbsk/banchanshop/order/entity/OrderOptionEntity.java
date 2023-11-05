package com.bbsk.banchanshop.order.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

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
@Table(name = "ORDER_OPTION")
public class OrderOptionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderOptionId;
	
	@ManyToOne
	@JoinColumn(name = "order_item_id")
	private OrderItemEntity orderItem;
	
	@Column(nullable = false)
	private String optionAmount;
	
	@Column(nullable = false)
	private String aoptionSpicy;
	
	@CreatedDate
	@Column(nullable = false)
	private LocalDateTime optionPickUp;

}
