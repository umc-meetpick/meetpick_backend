package com.umc.meetpick.common.jwt.factory;

import com.umc.meetpick.common.jwt.strategy.GoogleUserInfo;
import com.umc.meetpick.common.jwt.strategy.KakaoUserInfo;
import com.umc.meetpick.common.jwt.strategy.OAuth2UserInfo;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(Map<String, Object> attributes) {

        if (attributes.containsKey("id")) {
            return new KakaoUserInfo(attributes);
        } else if (attributes.containsKey("sub")) {
            return new GoogleUserInfo(attributes);
        }
        throw new IllegalArgumentException("Unsupported social login provider");
    }

}
