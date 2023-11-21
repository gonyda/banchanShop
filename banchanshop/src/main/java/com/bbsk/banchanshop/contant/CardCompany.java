package com.bbsk.banchanshop.contant;

public enum CardCompany {
    KAKAOPAY("카카오페이"),
    SHINHANCARD("신한카드");

    private final String name;

    CardCompany(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
