package com.nhnacademy.parkinglot;

import com.nhnacademy.parkinglot.exception.NoParkingSpaceException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ParkingLot {
    Entrance entrance = new Entrance();
    Exit exit = new Exit();

    List<Entrance> entrances = new ArrayList<>();
    List<Exit> exits = new ArrayList<>();

    HashMap<Car ,String> spaces = new HashMap<>();
    List<String> spaceCheckList = new ArrayList<>();

    void addEntranceAndExit(int n){
        for (int i = 0; i < n; i++) {
            entrances.add(new Entrance());
            exits.add(new Exit());
        }
    }

    boolean inputParkingLot(Car car, String spaceName) {
        if(spaceCheckList.contains(spaceName)){
            throw new NoParkingSpaceException();
        }

        spaces.put(car, spaceName);
        return true;
    }

}
