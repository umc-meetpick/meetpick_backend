package com.umc.meetpick.enums;

public enum MeetType {
    ONLINE("온라인"),
    OFFLINE("오프라인"),
    ALL("같이");

    private final String koreaName;

    MeetType(String koreaName) {
        this.koreaName = koreaName;
    }
}
