package com.bbsk.banchanshop.contant;

public enum PaymentType {
	CARD("신용카드"), // 신용카드
	ACCOUNTTRANSFER("계좌이체"); // 계좌이체

	private String name;

	PaymentType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
