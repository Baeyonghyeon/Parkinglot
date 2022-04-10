package com.nhnacademy.parkinglot;

import com.nhnacademy.parkinglot.exception.InsufficientCashException;
import com.nhnacademy.parkinglot.exception.MinusTimeException;
import com.nhnacademy.parkinglot.exception.NoAccessParkingLot;
import com.nhnacademy.parkinglot.exception.NoParkingSpaceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

class EntranceTest {
    ParkingLot parkingLot;
    int number = 1234;
    LocalDateTime parkingStartTime = LocalDateTime.of(2022, 4, 9, 10, 0, 0);
    BigDecimal amount = BigDecimal.valueOf(2000L);
    Segment segment = Segment.segmentA;
    String paycoId = "coco";
    Coupon coupon = Coupon.EMPTY;
    Car car = new Car(number, parkingStartTime, amount, segment, paycoId, coupon);


    @BeforeEach
    void setup() {
        parkingLot = new ParkingLot();
    }

    @DisplayName("주차장에 들어오는 차 번호스캔")
    @Test
    void invalidScanNumber() {
        Entrance entrance = mock(Entrance.class);
        int number = 1234;

        when(entrance.scan(number)).thenReturn(car);
        assertThat(entrance.scan(number)).isEqualTo(car);
        verify(entrance, times(1)).scan(number);
    }

    @DisplayName("A-1 공간이 없음")
    @Test
    void noParkingSpace() {
        String spaceName = "A-1";

        parkingLot.spaceCheckList.add(spaceName);

        assertThatThrownBy(() -> parkingLot.inputParkingLot(car, spaceName))
                .isInstanceOf(NoParkingSpaceException.class)
                .hasMessage("해당 주차 공간이 없습니다.");
    }

    @DisplayName("A-1 공간이 있음")
    @Test
    void ParkingSpace() {
        String spaceName = "A-1";

        assertThat(parkingLot.inputParkingLot(car, spaceName)).isTrue();
    }

    @DisplayName("주차한 시간이 음수일때")
    @Test
    void failParkingTime() {
        Exit exit = new Exit();

        LocalDateTime startDateTime = LocalDateTime.of(2022, 4, 9, 10, 30, 0);

        Car car = new Car(number, startDateTime, amount, segment, paycoId, coupon);

        assertThatThrownBy(() -> exit.getParkingTime(car))
                .isInstanceOf(MinusTimeException.class);
    }

    @DisplayName("주차한 시간 구하기")
    @Test
    void successParkingTime() {
        Exit exit = new Exit();
        long time = 30;

        assertThat(exit.getParkingTime(car)).isEqualTo(time);
    }

    @Disabled("요금표 변경으로 인해 테스트를 제외합니다.")
    @DisplayName("주차 요금 계산")
    @Test
    void parkingFee() {
        Exit exit = new Exit();
        long parkingTimeSeconds1 = 1179;
        long parkingTimeSeconds2 = 1800;
        long parkingTimeSeconds3 = 1801;
        long parkingTimeSeconds4 = 3000; //50분
        long parkingTimeSeconds5 = 3660; //61분
        long parkingTimeSeconds6 = 21600; //6시간

        assertThat(exit.parkingFee(parkingTimeSeconds1, car)).isEqualTo(1_000L);
        assertThat(exit.parkingFee(parkingTimeSeconds2, car)).isEqualTo(1_000L);
        assertThat(exit.parkingFee(parkingTimeSeconds3, car)).isEqualTo(1_500L);
        assertThat(exit.parkingFee(parkingTimeSeconds4, car)).isEqualTo(2_000L);
        assertThat(exit.parkingFee(parkingTimeSeconds5, car)).isEqualTo(3_000L);
        assertThat(exit.parkingFee(parkingTimeSeconds6, car)).isEqualTo(10_000L);
    }

    @DisplayName("잔액 부족")
    @Test
    void failPayment() {
        Exit exit = new Exit();
        long parkingFee = 4000L;

        assertThatThrownBy(() -> exit.payment(car, parkingFee))
                .isInstanceOf(InsufficientCashException.class);

    }

    @DisplayName("일반차량 결제 성공")
    @Test
    void successPayment() {
        Exit exit = new Exit();
        int number = 2000;
        Segment segment = Segment.segmentC;
        long parkingFee = 1000L;
        Car car = new Car(number, parkingStartTime, amount, segment, paycoId, coupon);

        assertThat(exit.payment(car, parkingFee)).isEqualTo(BigDecimal.valueOf(1000));
    }

    @DisplayName("주차장 입출구 n개 만들기")
    @Test
    void countEntranceAndExit() {
        ParkingLot parkingLot = mock(ParkingLot.class);
        int n = 3;

        parkingLot.addEntranceAndExit(n);
    }

    @DisplayName("리뉴얼된 주차 요금 계산")
    @Test
    void newParkingFee() {
        Exit exit = new Exit();
        long parkingTimeSeconds1 = 1179;
        long parkingTimeSeconds2 = 1800;
        long parkingTimeSeconds3 = 1801;
        long parkingTimeSeconds4 = 3000; //50분
        long parkingTimeSeconds5 = 3660; //61분
        long parkingTimeSeconds6 = 21600; //6시간

        assertThat(exit.parkingFee(parkingTimeSeconds1,car)).isEqualTo(0L);
        assertThat(exit.parkingFee(parkingTimeSeconds2,car)).isEqualTo(0L);
        assertThat(exit.parkingFee(parkingTimeSeconds3,car)).isEqualTo(1000L);
        assertThat(exit.parkingFee(parkingTimeSeconds4,car)).isEqualTo(1000L);
        assertThat(exit.parkingFee(parkingTimeSeconds5,car)).isEqualTo(1500L);
        assertThat(exit.parkingFee(parkingTimeSeconds6,car)).isEqualTo(15_000L);
    }

    @DisplayName("대형차 주차 불가")
    @Test
    void noEntry() {
        Entrance entrance = new Entrance();
        int number = 1234;
        Segment segment = Segment.segmentF;

        assertThatThrownBy(() -> entrance.scan(number, segment))
                .isInstanceOf(NoAccessParkingLot.class);
    }

    @DisplayName("소형차 50% 할인")
    @Test
    void segmentA_Discount() {
        Exit exit = new Exit();
        long parkingFee = 1000L;

        assertThat(exit.payment(car, parkingFee)).isEqualTo(BigDecimal.valueOf(1500));
    }

    @DisplayName("경차할인 + 페이코 회원 할인")
    @Test
    void DiscountSet() {
        Exit exit = new Exit();
        long parkingFee = 1000L;
        String paycoId = "coco";

        exit.parkingSystem.paycoMembers.add(paycoId);

        assertThat(exit.payment(car, parkingFee)).isEqualTo(BigDecimal.valueOf(1550L));
    }

    @DisplayName("할인혜택 없음")
    @Test
    void nonDiscount() {
        Exit exit = new Exit();

        long parkingFee = 1000L;
        Segment segment = Segment.segmentC;

        Car car = new Car(number, parkingStartTime, amount, segment, paycoId, coupon);

        assertThat(exit.payment(car, parkingFee)).isEqualTo(BigDecimal.valueOf(1000));
    }

    //시간 주차권이 존재합니다.
    //3시간 주차 후 2시간 주차권을 제시하면, 1시간 요금만 정산하면 됩니다.
    //59분 주차 후 1시간 주차권을 제시하면, 무료입니다.

    @DisplayName("주차권 할인만 적용")
    @Test
    void onlyCouponDiscount () {
        Exit exit = new Exit();
        int number = 2000;
        Segment segment = Segment.segmentC;
        Coupon coupon1 = Coupon.TWO_HOUR;
        Coupon coupon2 = Coupon.ONE_HOUR;
        String paycoId = "apple";

        exit.parkingSystem.paycoMembers.add(paycoId);

        Car car1 = new Car(number, parkingStartTime, amount, segment, paycoId, coupon1);
        Car car2 = new Car(number, parkingStartTime, amount, segment, paycoId, coupon2);

        assertThat(exit.parkingFee(10800, car1)).isEqualTo(1000L);
        assertThat(exit.parkingFee(3540, car2)).isEqualTo(0L);
    }
}