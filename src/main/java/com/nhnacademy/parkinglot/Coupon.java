package com.nhnacademy.parkinglot;

public enum Coupon {
    EMPTY(0), ONE_HOUR(3600), TWO_HOUR(7200);

    int timeSec;

    Coupon(int timeSec) {
        this.timeSec = timeSec;
    }
}
