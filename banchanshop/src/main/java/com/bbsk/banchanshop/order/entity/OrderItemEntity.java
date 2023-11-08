package com.bbsk.banchanshop.order.entity;

import com.bbsk.banchanshop.banchan.entity.BanchanEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "ORDER_ITEM")
@ToString
public class OrderItemEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderItemId;

	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", nullable = false)
	private OrdersEntity order;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "banchan_id", nullable = false)
	private BanchanEntity banchan;

	@Column(nullable = false)
	private int quantity;

	@Column(nullable = false)
	private int totalPrice;
}
