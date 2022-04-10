package com.nhnacademy.parkinglot;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Car {

    private int number;
    private LocalDateTime parkingStartTime;
    private BigDecimal amount;
    private Segment segment;
    private String paycoId;
    private Coupon coupon;

    public Car (int number){
        this(number, Segment.segmentC);
    }

    public Car(int number, LocalDateTime parkingStartTime, BigDecimal amount, Segment segment, String paycoId, Coupon coupon) {
        this.number = number;
        this.parkingStartTime = parkingStartTime;
        this.amount = amount;
        this.segment = segment;
        this.paycoId = paycoId;
        this.coupon = coupon;
    }

    public Car(int number, Segment segment) {
        this.number = number;
        this.segment = segment;
    }

    public LocalDateTime getParkingStartTime() {
        return parkingStartTime;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Segment getSegment() {
        return segment;
    }

    public String getPaycoId() {
        return paycoId;
    }

    public Coupon getCoupon() {
        return coupon;
    }
}
