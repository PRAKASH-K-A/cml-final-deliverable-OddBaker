package com.trading.g1m5.enums;

public enum EventVersion {
    V1("v1");

    private final String value;

    EventVersion(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
