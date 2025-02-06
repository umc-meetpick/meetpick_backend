package com.umc.meetpick.common.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umc.meetpick.common.response.ApiResponse;
import com.umc.meetpick.common.response.status.SuccessCode;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.enums.SocialType;
import com.umc.meetpick.repository.member.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private static final Map<Long, String> tokenStorage = new ConcurrentHashMap<>(); // ✅ JWT 토큰 저장소

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

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            SocialType socialType = detectSocialType(attributes);
            Long socialId = extractSocialId(attributes, socialType);

            log.info("🌐 로그인한 소셜 서비스: {}", socialType);
            log.info("✅ OAuth2 로그인 사용자 socialId: {}", socialId);

            Optional<Member> existingMember = memberRepository.findBySocialId(socialId);

            String token = null;

            if (existingMember.isPresent()) {
                // ✅ 기존 회원 로그인 처리
                Member member = existingMember.get();
                log.info("🎯 기존 회원 로그인 성공! memberId={}", member.getId());

                // ✅ 기존 토큰이 있으면 재사용, 없으면 새로 생성
                token = tokenStorage.getOrDefault(member.getId(), jwtUtil.generateToken(member.getId()));
                tokenStorage.put(member.getId(), token);

                log.info("✅ JWT 토큰 재사용 또는 발급 완료: {}", token);

                // ✅ SecurityContext에 사용자 정보 저장
                SecurityContextHolder.getContext().setAuthentication(authentication);

                // 로그인 성공 시
                response.setContentType("application/json;charset=UTF-8");
                response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
                response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
                String redirectUrl = "http://localhost:5173/looking?token=" + token;
                response.sendRedirect(redirectUrl);

            } else {
                // ✅ 신규 회원 가입 처리
                Member newMember = Member.builder()
                        .socialId(socialId)
                        .socialType(socialType)
                        .build();

                memberRepository.save(newMember);

                log.info("🎉 신규 회원 가입 진행! memberId={}", newMember.getId());

                token = jwtUtil.generateToken(newMember.getId());
                tokenStorage.put(newMember.getId(), token);
                log.info("✅ JWT 토큰 발급 완료: {}", token);

                // ✅ JWT 토큰 생성 및 저장 -> 소셜 아이디로 임시로 토큰 생성
                // TODO 나중에는 권한 설정 등 해서 바꾸기
                // TODO 회원가입 페이지로 리다이렉트
                // TODO 권한 설정하기
                // 기존 회원 로그인 또는 신규 회원 가입 시
                response.setContentType("application/json;charset=UTF-8");
                response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
                response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
                String redirectUrl = "http://localhost:5173/signup?token=" + token;
                response.sendRedirect(redirectUrl);
                response.getWriter().write(objectMapper.writeValueAsString(ApiResponse.of(SuccessCode._ADDITIONAL_INFO, token)));
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
