package com.bbsk.banchanshop.contant;

public enum OrderOption {
    MILD("덜 맵게"), // 덜 맵게
    MEDIUM("보통"), // 보통
    SPICY("맵게"), // 맵게

    SMALL("적게"), // 적게
    REGULAR("보통"), // 보통
    LARGE("많이"); // 많이

    private final String name;

    OrderOption(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
