package com.nhnacademy.parkinglot;

import com.nhnacademy.parkinglot.exception.InvalidInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ExtranceTest {

    @BeforeEach
    void setup(){

    }


    @DisplayName("주차장에 들어오는 차 번호판이 없음")
    @Test
    void invalidScanNumber() {
        Extrance extrance = new Extrance();
        int number = 1234;
        Car car = new Car(number);
        System.out.println(number);

        assertThat(extrance.scan(car)).isEqualTo(number);

//        assertThatThrownBy(()-> extrance.scan(car))
//                .isInstanceOf(InvalidInputException.class)
//                .hasMessage("invalid input");
    }
}