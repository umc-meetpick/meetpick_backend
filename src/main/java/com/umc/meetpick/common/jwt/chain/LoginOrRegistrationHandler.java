package com.umc.meetpick.common.jwt.chain;

import com.umc.meetpick.common.jwt.strategy.OAuth2UserInfo;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.repository.member.MemberRepository;
import com.umc.meetpick.common.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

import static com.umc.meetpick.common.jwt.OAuth2AuthenticationSuccessHandler.tokenStorage;

// 멤버의 가입 여부를 확인하고 토큰을 발급하는 클래스
@Slf4j
@Component
public class LoginOrRegistrationHandler implements AuthenticationHandler {

    private AuthenticationHandler next;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    public LoginOrRegistrationHandler(MemberRepository memberRepository, JwtUtil jwtUtil) {
        this.memberRepository = memberRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void setNext(AuthenticationHandler nextHandler) {
        this.next = nextHandler;
    }

    @Override
    public void handle(AuthContext context, HttpServletRequest request, HttpServletResponse response) throws IOException {

        log.info("LoginOrRegistrationHandler 시작");

        OAuth2UserInfo userInfo = context.getUserInfo();
        Long socialId = userInfo.getSocialId();

        Member member = memberRepository.findBySocialId(socialId)
                .map(m -> {
                    log.info("🎯 기존 회원 로그인 성공! memberId={}", m.getId());
                    context.setNewMember(!m.isVerified()); // verified되지 않은 사용자는 회원가입 진행
                    return m;
                })
                .orElseGet(() -> {
                    Member newMember = Member.builder()
                            .socialId(socialId)
                            .socialType(userInfo.getSocialType())
                            .build();
                    memberRepository.save(newMember);
                    context.setNewMember(true);
                    log.info("🎉 신규 회원 가입 진행! memberId={}", newMember.getId());
                    return newMember;
                });


        context.setMember(member);

        // JWT 토큰 발급
        String token = tokenStorage.getOrDefault(member.getId(), jwtUtil.generateToken(member.getId()));
        tokenStorage.put(member.getId(), token);

        log.info("✅ JWT 토큰 재사용 또는 발급 완료: {}", token);

        context.setToken(token);

        if (next != null) {
            next.handle(context, request, response);
        }
    }
}
