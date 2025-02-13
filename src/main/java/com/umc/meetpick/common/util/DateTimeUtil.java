package com.umc.meetpick.common.util;

import java.time.Duration;
import java.time.LocalDateTime;

public class DateTimeUtil {

    public static String getTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return "알 수 없음";
        }

        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(localDateTime, now);

        long seconds = duration.getSeconds();
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        if (seconds < 60) {
            return seconds + "초 전";
        } else if (minutes < 60) {
            return minutes + "분 전";
        } else if (hours < 24) {
            return hours + "시간 전";
        } else if (days < 7) {
            return days + "일 전";
        } else {
            return localDateTime.toLocalDate().toString(); // "YYYY-MM-DD" 형식
        }
    }

}
