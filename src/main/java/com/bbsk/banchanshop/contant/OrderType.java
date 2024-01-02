package com.bbsk.banchanshop.contant;

public enum OrderType {
	ORDER("일반주문"), // 일반주문
	PREORDER("예약주문"); // 예약주문

	private String name;

	OrderType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
