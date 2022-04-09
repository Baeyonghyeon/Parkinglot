package com.nhnacademy.parkinglot;

import com.nhnacademy.parkinglot.exception.NoParkingSpaceException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ParkingLot {
    Extrance extrance = new Extrance();
    Exit exit = new Exit();

    HashMap<Car ,String> spaces = new HashMap<>();
    List<String> spaceCheckList = new ArrayList<>();

    boolean inputParkingLot(Car car, String spaceName) {
        if(spaceCheckList.contains(spaceName)){
            throw new NoParkingSpaceException();
        }

        spaces.put(car, spaceName);
        return true;
    }

}
