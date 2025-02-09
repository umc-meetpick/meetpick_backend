package com.umc.meetpick.common.jwt.strategy;

import com.umc.meetpick.enums.SocialType;

public interface OAuth2UserInfo {
    Long getSocialId();
    SocialType getSocialType();
}