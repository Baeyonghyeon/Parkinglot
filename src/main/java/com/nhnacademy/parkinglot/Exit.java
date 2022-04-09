package com.nhnacademy.parkinglot;

import com.nhnacademy.parkinglot.exception.MinusTimeException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Exit {

    public long pay(long parkingTimeSeconds) {

        long days = parkingTimeSeconds / 86400;
        long seconds = parkingTimeSeconds % 86400;
        long dayFee = days * 10000;
        long secFee = 1000;

        if((seconds - 1800) % 600 == 0){
            secFee += ((seconds - 1800) / 600) * 500;
        }

        if((seconds - 1800) % 600 != 0){
            secFee += (((seconds - 1800) / 600)+1) * 500;
        }

        if(seconds <= 1800){
            secFee = 1000;
        }
        if (seconds > 12600){
            secFee = 10000;
        }

        return dayFee + secFee;
    }

    public long getParkingTime(Car car){
        LocalDateTime startDateTime = car.getParkingStartTime();
        LocalDateTime targetDateTime = LocalDateTime.now();

        long getSecondsDifference = ChronoUnit.SECONDS.between(startDateTime, targetDateTime);

        if(getSecondsDifference < 0){
            throw new MinusTimeException();
        }

        return getSecondsDifference;
    }
}



