package com.nhnacademy.parkinglot;

import com.nhnacademy.parkinglot.exception.NoAccessParkingLot;

public class Entrance {

    public Car scan(int number){
        return new Car(number);
    }

    public Car scan(int number, Segment segment){

        if (segment.equals(Segment.segmentF)){
            throw new NoAccessParkingLot();
        }

        return new Car(number, segment);
    }

}
