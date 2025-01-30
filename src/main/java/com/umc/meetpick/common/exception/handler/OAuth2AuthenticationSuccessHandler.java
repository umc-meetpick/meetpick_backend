package com.umc.meetpick.common.exception.handler;

import com.umc.meetpick.entity.Member;
import com.umc.meetpick.enums.SocialType;
import com.umc.meetpick.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        log.info("✅ OAuth2 인증 성공 - 사용자 처리 시작");

        if (!(authentication.getPrincipal() instanceof OAuth2User oAuth2User)) {
            log.error("⚠️ OAuth2User 정보를 가져오지 못했습니다.");
            response.sendRedirect("/login?error");
            return;
        }

        Map<String, Object> attributes = oAuth2User.getAttributes();
        log.info("🔍 OAuth2 사용자 속성: {}", attributes);

        try {
            SocialType socialType = detectSocialType(attributes);
            Long socialId = extractSocialId(attributes, socialType);

            log.info("🌐 로그인한 소셜 서비스: {}", socialType);
            log.info("✅ OAuth2 로그인 사용자 socialId: {}", socialId);

            Optional<Member> existingMember = memberRepository.findBySocialId(socialId);

            if (existingMember.isPresent()) {
                // ✅ 기존 회원이면 바로 홈으로 이동
                Member member = existingMember.get();
                log.info("🎯 기존 회원 로그인 성공! memberId={}", member.getId());

                SecurityContextHolder.getContext().setAuthentication(authentication);
                request.getSession().setAttribute("memberId", member.getId());

                response.sendRedirect("/home");
            } else {
                // ✅ 신규 회원이면 약관 동의 페이지로 이동
                Member newMember = Member.builder()
                        .socialId(socialId)
                        .socialType(socialType)
                        .build();
                memberRepository.save(newMember);

                log.info("🎉 신규 회원 가입 진행! memberId={}", newMember.getId());

                String encodedMemberId = URLEncoder.encode(newMember.getId().toString(), StandardCharsets.UTF_8);
                response.sendRedirect("/terms?memberId=" + encodedMemberId);
            }

        } catch (Exception e) {
            log.error("❌ OAuth2 로그인 처리 중 오류 발생: {}", e.getMessage(), e);
            response.sendRedirect("/login?error");
        }
    }

    private SocialType detectSocialType(Map<String, Object> attributes) {
        if (attributes.containsKey("id")) {
            return SocialType.KAKAO;
        } else if (attributes.containsKey("sub")) {
            return SocialType.GOOGLE;
        }
        throw new RuntimeException("⚠️ 알 수 없는 소셜 로그인 서비스");
    }

    private Long extractSocialId(Map<String, Object> attributes, SocialType socialType) {
        return Optional.ofNullable(
                        switch (socialType) {
                            case KAKAO -> attributes.get("id");
                            case GOOGLE -> attributes.get("sub");
                            default -> throw new IllegalArgumentException("⚠️ 지원되지 않는 소셜 로그인 서비스: " + socialType);
                        })
                .map(id -> {
                    if (id instanceof Number) {
                        return ((Number) id).longValue();
                    } else {
                        return Long.parseLong(id.toString());
                    }
                })
                .orElseThrow(() -> new RuntimeException("⚠️ 소셜 로그인 ID를 찾을 수 없음"));
    }
}
