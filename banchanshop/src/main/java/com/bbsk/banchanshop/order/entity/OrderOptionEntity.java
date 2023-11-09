package com.bbsk.banchanshop.order.entity;

import java.time.LocalDateTime;

import com.bbsk.banchanshop.contant.OrderOption;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

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
	
	@OneToOne
	@JoinColumn(name = "order_item_id")
	private OrderItemEntity orderItem;

	@Enumerated(EnumType.STRING)
	@Column
	private OrderOption optionAmount;

	@Enumerated(EnumType.STRING)
	@Column
	private OrderOption optionSpicy;
	
	@Column(nullable = false)
	private LocalDateTime optionPickUp;

}
