package com.umc.meetpick.common.jwt.chain;

import com.umc.meetpick.entity.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

@Getter
@Setter
public class AuthContext {

    private Map<String, Object> attributes;
    private OAuth2User userInfo;
    private Member member;
    private String token;
    private boolean isNewMember;

    public AuthContext(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

}

