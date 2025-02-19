package com.umc.meetpick.common.util;

import java.util.HashSet;
import java.util.Set;


//아직 삭제 X
public class TimeConverter {

    public static Set<Integer> convertTimeOfDay(String timeOfDay) {
        Set<Integer> hours = new HashSet<>();

        switch(timeOfDay.toLowerCase()) {
            case "아침":
                for(int i = 5; i <= 10; i++) {
                    hours.add(i);
                }
                break;
            case "점심":
                for(int i = 11; i <= 16; i++) {
                    hours.add(i);
                }
                break;
            case "저녁":
                for(int i = 17; i <= 22; i++) {
                    hours.add(i);
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid time of day: " + timeOfDay);
        }

        return hours;
    }
}