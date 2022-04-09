package com.nhnacademy.parkinglot.exception;

public class MinusTimeException extends IllegalArgumentException{
    public MinusTimeException() {
        super("시간이 음수에요.");
    }
}
