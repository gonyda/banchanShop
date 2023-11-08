package com.bbsk.banchanshop.order.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.bbsk.banchanshop.contant.OrderType;
import com.bbsk.banchanshop.contant.PaymentType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import com.bbsk.banchanshop.user.entity.UserEntity;

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
@Table(name = "ORDERS")
public class OrdersEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private UserEntity user;

	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
	private List<OrderItemEntity> orderItems = null;

	@Enumerated(value = EnumType.STRING)
	@Column(nullable = false)
	private OrderType orderType;

	@Enumerated(value = EnumType.STRING)
	@Column(nullable = false)
	private PaymentType paymentType;

	@Column(nullable = false)
	private String address;
	
	@Column(nullable = false)
	private int totalPrice;
	
	@CreationTimestamp
	@Column(nullable = false)
	private LocalDateTime orderDate;

	public void setOrderItems(List<OrderItemEntity> orderItems) {
		this.orderItems = orderItems;
	}
}
