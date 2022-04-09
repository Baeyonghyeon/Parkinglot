package com.nhnacademy.parkinglot;

import com.nhnacademy.parkinglot.exception.NoParkingSpaceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

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

    @DisplayName("주차 시간이 음수일때")
    @Test
    void failParkingTime(){
        int number = 1234;
        String spaceName = "A-1";

        LocalDateTime startDateTime = LocalDateTime.of(2022, 4, 9, 10, 0, 0);
        LocalDateTime targetDateTime = LocalDateTime.of(2022, 4, 9, 9, 30, 0);

        Car car = new Car(number, startDateTime);


    }



}