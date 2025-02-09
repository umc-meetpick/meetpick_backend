package com.umc.meetpick.common.jwt.strategy;

import com.umc.meetpick.enums.SocialType;
import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo {

    private final Map<String, Object> attributes;

    public KakaoUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public Long getSocialId() {
        Object id = attributes.get("id");
        return id instanceof Number ? ((Number) id).longValue() : Long.parseLong(id.toString());
    }

    @Override
    public SocialType getSocialType() {
        return SocialType.KAKAO;
    }
}
