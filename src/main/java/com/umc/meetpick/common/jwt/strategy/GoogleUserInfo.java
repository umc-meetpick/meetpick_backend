package com.umc.meetpick.common.jwt.strategy;

import com.umc.meetpick.enums.SocialType;
import java.util.Map;

public class GoogleUserInfo implements OAuth2UserInfo {

    private Map<String, Object> attributes;

    public GoogleUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public Long getSocialId() {
        Object sub = attributes.get("sub");
        return sub instanceof Number ? ((Number) sub).longValue() : Long.parseLong(sub.toString());
    }

    @Override
    public SocialType getSocialType() {
        return SocialType.GOOGLE;
    }
}
