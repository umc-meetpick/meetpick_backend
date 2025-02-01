package com.umc.meetpick.service;

import com.umc.meetpick.JwtUtil;
import com.umc.meetpick.dto.KakaoTokenResponseDTO;
import com.umc.meetpick.dto.KakaoUserInfoResponseDTO;
import com.umc.meetpick.dto.KakaoLoginResponseDTO;
import com.umc.meetpick.entity.KakaoMember;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.enums.SocialType;
import com.umc.meetpick.repository.KakaoMemberRepository;
import com.umc.meetpick.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoService {

    private final MemberRepository memberRepository;
    private final KakaoMemberRepository kakaoMemberRepository;
    private final JwtUtil jwtUtil;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;

    private final String KAUTH_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    private final String KAPI_USER_URL = "https://kapi.kakao.com/v2/user/me";

    /**
     * 🔑 카카오 로그인 후 JWT 토큰 발급
     */
    public KakaoLoginResponseDTO kakaoLogin(String code) {
        log.info("🔍 [KakaoService] 카카오 로그인 요청 - code: {}", code);

        // 🔹 1. 카카오 OAuth2 액세스 토큰 요청
        KakaoTokenResponseDTO tokenResponse = requestAccessToken(code);
        String accessToken = Optional.ofNullable(tokenResponse)
                .map(KakaoTokenResponseDTO::getAccessToken)
                .orElseThrow(() -> new RuntimeException("카카오 액세스 토큰을 가져올 수 없습니다."));

        log.info("✅ [KakaoService] Access Token: {}", accessToken);

        // 🔹 2. 카카오에서 사용자 정보 가져오기
        KakaoUserInfoResponseDTO kakaoUserInfo = requestKakaoUserInfo(accessToken);
        Long socialId = kakaoUserInfo.getSocialId();

        log.info("✅ [KakaoService] 가져온 사용자 정보: socialId={}, nickname={}", socialId, kakaoUserInfo.getNickname());

        // 🔹 3. DB에서 사용자 조회 (없으면 회원가입)
        Member member = findOrCreateMember(kakaoUserInfo);

        if (member == null) {
            throw new RuntimeException("카카오 로그인 처리 중 문제가 발생했습니다.");
        }

        log.info("✅ [KakaoService] 로그인한 사용자 memberId: {}", member.getId());

        // 🔹 4. JWT 토큰 발급
        String jwtToken = jwtUtil.generateToken(member.getId());

        return new KakaoLoginResponseDTO(jwtToken, member.getId());
    }

    /**
     * 🆔 카카오 OAuth2 액세스 토큰 요청
     */
    private KakaoTokenResponseDTO requestAccessToken(String code) {
        log.info("🔄 [KakaoService] 카카오 액세스 토큰 요청");
        System.out.println(redirectUri);
        return WebClient.create(KAUTH_TOKEN_URL)
                .post()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("client_id", clientId)
                        .queryParam("client_secret", clientSecret)
                        .queryParam("code", code)
                        .queryParam("redirect_uri", redirectUri)
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .retrieve()
                .bodyToMono(KakaoTokenResponseDTO.class)
                .block();
    }

    /**
     * 🔍 카카오 사용자 정보 가져오기
     */
    private KakaoUserInfoResponseDTO requestKakaoUserInfo(String accessToken) {
        log.info("🔄 [KakaoService] 카카오 사용자 정보 요청");

        return WebClient.create(KAPI_USER_URL)
                .get()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(KakaoUserInfoResponseDTO.class)
                .block();
    }

    /**
     * 🏠 기존 회원 조회 및 신규 회원 생성 (중복 저장 방지)
     */
    private Member findOrCreateMember(KakaoUserInfoResponseDTO kakaoUserInfo) {
        Long socialId = kakaoUserInfo.getSocialId();
        log.info("🔍 [KakaoService] findOrCreateMember - socialId: {}", socialId);

        // 1️⃣ 기존 Member 조회 (중복 방지)
        Optional<Member> existingMember = memberRepository.findBySocialId(socialId);
        if (existingMember.isPresent()) {
            log.info("✅ 기존 Member 존재 - Member ID: {}", existingMember.get().getId());
            return existingMember.get();
        }

        // 2️⃣ 기존 KakaoMember 조회
        Optional<KakaoMember> existingKakaoMember = kakaoMemberRepository.findBySocialId(socialId);
        KakaoMember kakaoMember;

        if (existingKakaoMember.isPresent()) {
            kakaoMember = existingKakaoMember.get();
            if (kakaoMember.getMember() != null) {
                log.info("✅ 기존 KakaoMember가 Member와 연결됨 - Member ID: {}", kakaoMember.getMember().getId());
                return kakaoMember.getMember();
            }
            log.warn("⚠ 기존 KakaoMember에 Member 연결이 없음. 새로운 Member 생성 필요.");
        } else {
            // 3️⃣ 새 KakaoMember 생성
            log.info("🎉 새로운 KakaoMember 생성");
            kakaoMember = KakaoMember.builder()
                    .socialId(socialId)
                    .nickname(kakaoUserInfo.getNickname())
                    .gender(kakaoUserInfo.getGender())
                    .birthday(kakaoUserInfo.getBirthday())
                    .socialType(SocialType.KAKAO)
                    .build();
            kakaoMember = kakaoMemberRepository.save(kakaoMember);
        }

        // 4️⃣ 새 Member 생성 및 연결
        log.info("🎉 새로운 Member 생성");
        Member newMember = Member.builder()
                .socialId(socialId)
                .socialType(SocialType.KAKAO)
                .university("Unknown University")  // 기본 값 설정
                .build();
        newMember = memberRepository.save(newMember);

        kakaoMember.setMember(newMember);
        kakaoMemberRepository.save(kakaoMember);  // ✅ 연결 후 저장

        log.info("✅ 새 KakaoMember와 Member 연결 완료 - Member ID: {}", newMember.getId());
        return newMember;
    }
}
