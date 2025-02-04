package com.umc.meetpick.service;

import com.umc.meetpick.common.response.ApiResponse;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.enums.University;
import com.umc.meetpick.repository.member.MemberRepository;
import com.umc.meetpick.common.response.status.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailVerificationService {

    private final MemberRepository memberRepository;
    private final RestTemplate restTemplate;

    private static final String API_KEY = "c5efad4c-356f-4989-949f-cbb056439ba6";
    private static final String EMAIL_VERIFY_URL = "https://univcert.com/api/v1/certify";
    private static final String CODE_VERIFY_URL = "https://univcert.com/api/v1/certifycode";

    /**
     * ✅ 이메일 인증 코드 요청
     */
    public ApiResponse<String> sendVerificationCode(Long memberId, String email, String univName) {
        log.info("📧 이메일 인증 코드 요청 - memberId={}, email={}, university={}", memberId, email, univName);

        // 대학교명 형식 검증
        if (!isValidUniversityName(univName)) {
            return ApiResponse.onFailure(ErrorCode._BAD_REQUEST.getCode(),
                    "❌ 대학교명 형식 오류: 예) xx대학교와 같이 입력하세요.", null);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject requestBody = new JSONObject();
        requestBody.put("key", API_KEY);
        requestBody.put("email", email);
        requestBody.put("univName", StringEscapeUtils.unescapeHtml4(univName));
        requestBody.put("univ_check", true);

        HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers);
        ResponseEntity<String> response;

        try {
            response = restTemplate.postForEntity(EMAIL_VERIFY_URL, request, String.class);
        } catch (Exception e) {
            log.error("❌ 인증번호 요청 실패: {}", e.getMessage());
            return ApiResponse.onFailure(ErrorCode.SERVER_ERROR.getCode(),
                    "서버 오류 발생: " + e.getMessage(), null);
        }

        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("✅ 이메일 인증 코드 전송 성공");
            return ApiResponse.onSuccess("인증 코드가 전송되었습니다.");
        } else {
            log.error("❌ 이메일 인증 코드 전송 실패");
            return ApiResponse.onFailure(ErrorCode.SERVER_ERROR.getCode(), "인증번호 전송 실패", null);
        }
    }

    /**
     * ✅ 이메일 인증 코드 검증 및 대학교 정보 업데이트
     */
    @Transactional
    public ApiResponse<String> verifyCode(Long memberId, String email, String univName, int verificationCode) {
        log.info("🔍 이메일 인증 코드 검증 - memberId={}, email={}, university={}, code={}",
                memberId, email, univName, verificationCode);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject requestBody = new JSONObject();
        requestBody.put("key", API_KEY);
        requestBody.put("email", email);
        requestBody.put("univName", univName);
        requestBody.put("code", verificationCode);

        HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers);
        ResponseEntity<String> response;

        try {
            response = restTemplate.postForEntity(CODE_VERIFY_URL, request, String.class);
        } catch (Exception e) {
            log.error("❌ 인증 코드 검증 실패: {}", e.getMessage());
            return ApiResponse.onFailure(ErrorCode.SERVER_ERROR.getCode(),
                    "서버 오류 발생: " + e.getMessage(), null);
        }

        JSONObject responseBody = new JSONObject(response.getBody());
        boolean isSuccess = responseBody.getBoolean("success");

        if (response.getStatusCode().is2xxSuccessful() && isSuccess) {
            updateUniversity(memberId, univName);
            log.info("✅ 인증 성공 - 대학교 정보 업데이트 완료");
            return ApiResponse.onSuccess("인증 성공, 대학교 정보가 저장되었습니다.");
        } else {
            log.error("❌ 인증 실패: 코드가 일치하지 않음");
            return ApiResponse.onFailure(ErrorCode._BAD_REQUEST.getCode(), "인증 실패: 코드가 일치하지 않습니다.", null);
        }
    }

    /**
     * ✅ 대학교명 업데이트
     */
    @Transactional
    public void updateUniversity(Long memberId, String universityName) {
        log.info("🔹 대학교 정보 업데이트 - memberId={}, university={}", memberId, universityName.trim());

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + memberId));

        // ✅ 변경된 부분: 예외 발생 대신 UNKNOWN_UNIVERSITY 저장
        University universityEnum = University.fromString(universityName.trim());
        if (universityEnum == University.UNKNOWN_UNIVERSITY) {
            log.warn("⚠️ 알 수 없는 대학교: {}", universityName);
        }

        member.setUniversity(universityEnum);
        memberRepository.save(member);
    }

    /**
     * ✅ 대학교명 형식 검증
     */
    private boolean isValidUniversityName(String univName) {
        Pattern universityPattern = Pattern.compile(".*대학교$");
        return universityPattern.matcher(univName).matches();
    }
}
