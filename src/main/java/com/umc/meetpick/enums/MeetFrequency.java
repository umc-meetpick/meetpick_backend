package com.umc.meetpick.enums;

public enum MeetFrequency {

    //TODO 뭔가 좀 이상한데... 나중에 바꾸기
    ONETOTWO("1-2회"),
    THREETOFOUR("3-4회"),
    FIVETOSIX("5-6회");

    private final String koreanName;

    MeetFrequency(String koreanName) {
        this.koreanName = koreanName;
    }
}
