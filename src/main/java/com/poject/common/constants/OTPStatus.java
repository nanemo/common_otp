package com.poject.common.constants;

public enum OTPStatus {
    WAITING(0), VERIFIED(1), EXPIRED(2), LIMIT_EXCEED(3);
    private final int number;

    OTPStatus(int i) {
        number = i;
    }


    public int getStatus() {
        return number;
    }
}
