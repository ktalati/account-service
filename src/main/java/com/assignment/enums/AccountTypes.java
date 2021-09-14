package com.assignment.enums;

public enum AccountTypes {

    SAVINGS_ACCOUNT("SAVINGS_ACCOOUNT", "1"),
    CURRENT_ACCOUNT("CURRENT_ACCOUNT","2");

    private final String key;
    private final String value;

    AccountTypes(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

}