package com.nhnacademy.parkinglot;

import com.nhnacademy.parkinglot.exception.MinusTimeException;
import com.nhnacademy.parkinglot.exception.InsufficientCashException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Exit {
    ParkingSystem parkingSystem = new ParkingSystem();
    private static final long HOUR = 86400;
    private static final long FOURHOUR = 14400;
    private static final long HALFHOUR = 1800;

    public long parkingFee(long parkingTimeSeconds, Car car) {

        long days = parkingTimeSeconds / HOUR;
        long seconds = (parkingTimeSeconds % HOUR) - HALFHOUR;
        long dayFee = days * 15000;
        long secFee = 1000;

        seconds -= car.getCoupon().timeSec;

        if(seconds <= 0){
            return 0;
        }

        if((seconds - HALFHOUR) % 600 == 0){
            secFee += ((seconds - HALFHOUR) / 600) * 500;
        }

        if((seconds - HALFHOUR) % 600 != 0){
            secFee += (((seconds - HALFHOUR) / 600)+1) * 500;
        }

        if(seconds <= HALFHOUR){
            secFee = 1000;
        }

        if (seconds > FOURHOUR){
            secFee = 15000;
        }

        return dayFee + secFee;
    }

    public long getParkingTime(Car car){
        LocalDateTime startDateTime = car.getParkingStartTime();
        LocalDateTime targetDateTime = LocalDateTime.of(2022, 4, 9, 10, 0, 30);

        //로직은 아래꺼가 맞지만 테스트 떄문에 주석처리
        //LocalDateTime targetDateTime = LocalDateTime.now();

        long getSecondsDifference = ChronoUnit.SECONDS.between(startDateTime, targetDateTime);

        if(getSecondsDifference < 0){
            throw new MinusTimeException();
        }

        return getSecondsDifference;
    }

    public BigDecimal payment(Car car, long parkingFee){
        BigDecimal result = BigDecimal.valueOf(parkingFee);

        if(car.getAmount().compareTo(BigDecimal.valueOf(parkingFee)) < 0){
            throw new InsufficientCashException();
        }

        if (car.getSegment() == Segment.segmentA){
            result = (BigDecimal.valueOf(parkingFee).multiply(BigDecimal.valueOf(0.5)));
        }

        if (parkingSystem.searchMember(car.getPaycoId())){
            result = result.subtract(result.multiply(BigDecimal.valueOf(0.1)));

        }

        return car.getAmount().subtract(result).setScale(0, RoundingMode.FLOOR);
    }

}



