package com.umc.meetpick.enums;

public enum Hobby {
    READING("독서"),
    TRAVELING("여행"),
    SPORTS("운동"),
    COOKING("요리"),
    PAINTING("그림그리기"),
    GARDENING("정원가꾸기"),
    PHOTOGRAPHY("사진촬영"),
    SINGING("노래부르기"),
    DANCING("춤추기"),
    FISHING("낚시"),
    CYCLING("자전거타기"),
    HIKING("등산"),
    PLAYING_MUSICAL_INSTRUMENTS("악기연주"),
    WATCHING_MOVIES("영화감상"),
    PLAYING_VIDEO_GAMES("비디오게임"),
    WRITING("글쓰기"),
    CRAFTING("공예"),
    BAKING("베이킹"),
    YOGA("요가"),
    KNITTING("뜨개질"),
    COLLECTING("수집하기");

    private final String koreanName;

    Hobby(String koreanName) {
        this.koreanName = koreanName;
    }

    public String getKoreanName() {
        return koreanName;
    }
}

