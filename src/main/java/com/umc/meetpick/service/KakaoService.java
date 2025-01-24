package com.umc.meetpick.service;

import com.umc.meetpick.dto.KakaoTokenResponseDTO;
import com.umc.meetpick.dto.KakaoUserInfoResponseDTO;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.enums.Gender;
import com.umc.meetpick.enums.MemberRole;
import com.umc.meetpick.enums.MemberStatus;
import com.umc.meetpick.enums.SocialType;
import com.umc.meetpick.repository.MemberRepository;
import io.netty.handler.codec.http.HttpHeaderValues;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoService {

    private final MemberRepository memberRepository;

    @Value("${kakao.client_id}")
    private String clientId;

    private final String KAUTH_TOKEN_URL_HOST = "https://kauth.kakao.com";
    private final String KAUTH_USER_URL_HOST = "https://kapi.kakao.com";

    // AccessToken을 가져오는 메서드
    public String getAccessTokenFromKakao(String code) {
        KakaoTokenResponseDTO kakaoTokenResponseDto = WebClient.create(KAUTH_TOKEN_URL_HOST)
                .post()
                .uri(uriBuilder -> uriBuilder
                        .path("/oauth/token")
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("client_id", clientId)
                        .queryParam("code", code)
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException("Invalid Parameter")))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new RuntimeException("Internal Server Error")))
                .bodyToMono(KakaoTokenResponseDTO.class)
                .block();

        log.info("[Kakao Service] Access Token: {}", kakaoTokenResponseDto.getAccessToken());
        return kakaoTokenResponseDto.getAccessToken();
    }

    // 사용자 정보를 가져오는 메서드
    public KakaoUserInfoResponseDTO getUserInfo(String accessToken) {
        KakaoUserInfoResponseDTO userInfo = WebClient.create(KAUTH_USER_URL_HOST)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v2/user/me")
                        .build())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException("Invalid Parameter")))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new RuntimeException("Internal Server Error")))
                .bodyToMono(KakaoUserInfoResponseDTO.class)
                .block();

        log.info("[Kakao Service] User ID: {}", userInfo.getId());
        log.info("[Kakao Service] NickName: {}", userInfo.getKakaoAccount().getProfile().getNickName());
        return userInfo;
    }

    // 사용자 정보를 데이터베이스에 저장하고 memberId를 반환하는 메서드
    public Long saveKakaoUser(KakaoUserInfoResponseDTO userInfo) {
        // socialId로 중복 체크
        Optional<Member> existingMember = memberRepository.findBySocialId(userInfo.getId());
        if (existingMember.isPresent()) {
            log.info("[ Kakao Service ] 해당 사용자가 이미 존재합니다. Social ID: {}", userInfo.getId());
            return existingMember.get().getId();
        }

        // gender 처리
        String genderValue = userInfo.getKakaoAccount().getGender();
        Gender memberGender = Gender.MALE; // 기본값 설정
        if (genderValue != null) {
            memberGender = genderValue.equals("male") ? Gender.MALE : Gender.FEMALE;
        }

        // 생일 처리 (MMDD 형식 -> Date 변환)
        Date birthday = convertBirthday(userInfo.getKakaoAccount().getBirthday());

        // Member 저장
        Member member = Member.builder()
                .socialId(userInfo.getId()) // 카카오 로그인 ID
                .socialType(SocialType.KAKAO) // 소셜 타입
                .gender(memberGender) // 성별 처리
                .birthday(birthday) // 생일
                .university("Unknown University") // 기본값
                .role(MemberRole.MEMBER) // 기본값
                .status(MemberStatus.ACTIVE) // 기본값
                .build();

        memberRepository.save(member);
        log.info("[ Kakao Service ] Member 저장 완료 ---> ID: {}", member.getId());

        return member.getId();
    }

    // 생일 변환 로직
    private Date convertBirthday(String birthDay) {
        if (birthDay == null) {
            return new Date(); // 기본값
        }
        try {
            // MMDD 형식을 Date로 변환
            SimpleDateFormat sdf = new SimpleDateFormat("MMdd");
            Date parsedDate = sdf.parse(birthDay);

            // 연도를 추가 (기본값으로 2000년 설정)
            parsedDate.setYear(2000 - 1900); // Date에서 연도는 1900을 기준으로 계산
            return parsedDate;
        } catch (ParseException e) {
            log.error("[Kakao Service] 생일 변환 중 오류 발생: {}", e.getMessage());
            return new Date(); // 기본값
        }
    }
}
