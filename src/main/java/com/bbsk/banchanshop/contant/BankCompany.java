package com.bbsk.banchanshop.contant;

public enum BankCompany {
    SHINHANBANK("신한은행"),
    KOOKMINBANK("국민은행");

    private final String name;

    BankCompany(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
