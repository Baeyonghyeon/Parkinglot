package com.nhnacademy.parkinglot;

import com.nhnacademy.parkinglot.exception.MinusTimeException;
import com.nhnacademy.parkinglot.exception.NoParkingSpaceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

class ExtranceTest {
    ParkingLot parkingLot;

    @BeforeEach
    void setup() {
        parkingLot = new ParkingLot();
    }


    @DisplayName("주차장에 들어오는 차 번호스캔")
    @Test
    void invalidScanNumber() {
        Extrance extrance = mock(Extrance.class);
        int number = 1234;
        Car mycar = new Car(number);

        extrance.scan(number);

        when(extrance.scan(number)).thenReturn(mycar);
        assertThat(extrance.scan(number)).isEqualTo(mycar);
        verify(extrance, times(1)).scan(number);
//        assertThatThrownBy(()-> extrance.scan(car))
//                .isInstanceOf(InvalidInputException.class)
//                .hasMessage("invalid input");
    }

    @DisplayName("A-1 공간이 없음")
    @Test
    void noParkingSpace() {
        int number = 1234;
        String spaceName = "A-1";
        Car car1 = new Car(number);

        parkingLot.spaceCheckList.add(spaceName);

        assertThatThrownBy(() -> parkingLot.inputParkingLot(car1, spaceName))
                .isInstanceOf(NoParkingSpaceException.class)
                .hasMessage("해당 주차 공간이 없습니다.");
    }

    @DisplayName("A-1 공간이 있음")
    @Test
    void ParkingSpace() {
        int number = 1234;
        String spaceName = "A-1";
        Car car = new Car(number);

        assertThat(parkingLot.inputParkingLot(car, spaceName)).isTrue();
    }

    @DisplayName("주차한 시간이 음수일때")
    @Test
    void failParkingTime(){
        Exit exit = new Exit();
        int number = 1234;
        String spaceName = "A-1";

        LocalDateTime startDateTime = LocalDateTime.of(2022, 4, 9, 10, 30, 0);
        LocalDateTime targetDateTime = LocalDateTime.of(2022, 4, 9, 10, 0, 0);

        Car car = new Car(number, startDateTime);


        long seconds = ChronoUnit.SECONDS.between(startDateTime, targetDateTime);

        assertThatThrownBy(()-> exit.getParkingTime(car))
                .isInstanceOf(MinusTimeException.class);

    }

    @DisplayName("주차한 시간 구하기")
    @Test
    void successParkingTime(){
        Exit exit = new Exit();

        int number = 1234;
        String spaceName = "A-1";
        long time = 30;

        LocalDateTime startDateTime = LocalDateTime.of(2022, 4, 9, 10, 0, 0);
        LocalDateTime targetDateTime = LocalDateTime.of(2022, 4, 20, 10, 0, 0);;

        Car car = new Car(number, startDateTime);

        long days = ChronoUnit.DAYS.between(startDateTime, targetDateTime);
        long seconds = ChronoUnit.SECONDS.between(startDateTime, targetDateTime);
        System.out.println(days);
        System.out.println(seconds);
        assertThat(exit.getParkingTime(car)).isEqualTo(time);

    }

    @DisplayName("주차 요금 계산")
    @Test
    void parkingFee(){
        Exit exit = new Exit();
        long parkingTimeSeconds1 = 1179;
        long parkingTimeSeconds2 = 1800;
        long parkingTimeSeconds3 = 1801;
        long parkingTimeSeconds4 = 3000; //50분
        long parkingTimeSeconds5 = 3660; //61분
        long parkingTimeSeconds6 = 21600; //6시간


        assertThat(exit.pay(parkingTimeSeconds1)).isEqualTo(1_000L);
        assertThat(exit.pay(parkingTimeSeconds2)).isEqualTo(1_000L);
        assertThat(exit.pay(parkingTimeSeconds3)).isEqualTo(1_500L);
        assertThat(exit.pay(parkingTimeSeconds4)).isEqualTo(2_000L);
        assertThat(exit.pay(parkingTimeSeconds5)).isEqualTo(3_000L);
        assertThat(exit.pay(parkingTimeSeconds6)).isEqualTo(10_000L);
    }


}