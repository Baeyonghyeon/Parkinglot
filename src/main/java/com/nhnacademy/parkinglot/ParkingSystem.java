package com.nhnacademy.parkinglot;

import java.util.ArrayList;
import java.util.List;

public class ParkingSystem {

    List<String> paycoMembers = new ArrayList<>();

    public boolean searchMember(String id){
        return paycoMembers.contains(id);
    }
}
