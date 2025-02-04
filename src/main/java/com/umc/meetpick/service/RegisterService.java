package com.umc.meetpick.service;

import com.umc.meetpick.common.response.ApiResponse;
import com.umc.meetpick.dto.RegisterDTO;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.enums.Gender;
import com.umc.meetpick.enums.University;
import com.umc.meetpick.repository.member.MemberRepository;
import com.umc.meetpick.common.response.status.ErrorCode;
import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.util.regex.Pattern;

@Service
public class RegisterService {

    private final MemberRepository memberRepository;
    private final RestTemplate restTemplate;
    private static final String API_KEY = "c5efad4c-356f-4989-949f-cbb056439ba6";
    private static final String EMAIL_VERIFY_URL = "https://univcert.com/api/v1/certify";
    private static final String CODE_VERIFY_URL = "https://univcert.com/api/v1/certifycode";

    public RegisterService(MemberRepository memberRepository, RestTemplate restTemplate) {
        this.memberRepository = memberRepository;
        this.restTemplate = restTemplate;
    }

    /**
     * 회원 기본 정보 저장
     */
    @Transactional
    public ApiResponse<RegisterDTO.SignUpDTO> saveMemberProfile(Long memberId, RegisterDTO.SignUpDTO signUpDTO) {
        try {
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new RuntimeException("Member not found with id: " + memberId));

            Gender newGender = signUpDTO.getGender().equals("남성") ? Gender.MALE : Gender.FEMALE;
            Date newBirthday = Date.valueOf(signUpDTO.getBirthday());

            if (newGender.equals(member.getGender()) && newBirthday.equals(member.getBirthday())) {
                return ApiResponse.onSuccess(signUpDTO);
            }

            // 저장할 이름 설정
            member.setName(signUpDTO.getName());
            member.setGender(newGender);
            member.setBirthday(newBirthday);
            memberRepository.save(member);  // 업데이트 후 저장

            signUpDTO.setMemberId(memberId);
            return ApiResponse.onSuccess(signUpDTO);

        } catch (Exception e) {
            return ApiResponse.onFailure(ErrorCode.MEMBER_SIGNUP_ERROR.getCode(),
                    ErrorCode.MEMBER_SIGNUP_ERROR.getMessage(), null);
        }
    }

    /**
     * 이메일 인증 코드 요청
     */
    public ApiResponse<String> sendVerificationCode(Long memberId, String email, String univName) {
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
            return ApiResponse.onFailure(ErrorCode.SERVER_ERROR.getCode(),
                    "서버 오류 발생: " + e.getMessage(), null);
        }

        if (response.getStatusCode().is2xxSuccessful()) {
            return ApiResponse.onSuccess("인증 코드가 전송되었습니다.");
        } else {
            return ApiResponse.onFailure(ErrorCode.SERVER_ERROR.getCode(), "인증번호 전송 실패", null);
        }
    }

    /**
     * 이메일 인증 코드 검증
     */
    @Transactional
    public ApiResponse<String> verifyEmailCode(Long memberId, String email, String univName, int verificationCode) {
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
            return ApiResponse.onFailure(ErrorCode.SERVER_ERROR.getCode(),
                    "서버 오류 발생: " + e.getMessage(), null);
        }

        JSONObject responseBody = new JSONObject(response.getBody());
        boolean isSuccess = responseBody.getBoolean("success");

        if (response.getStatusCode().is2xxSuccessful() && isSuccess) {
            updateUniversity(memberId, univName);
            return ApiResponse.onSuccess("인증 성공, 대학교 정보가 저장되었습니다.");
        } else {
            return ApiResponse.onFailure(ErrorCode._BAD_REQUEST.getCode(), "인증 실패: 코드가 일치하지 않습니다.", null);
        }
    }

    /**
     * 대학교명 업데이트
     */
    @Transactional
    public void updateUniversity(Long memberId, String universityName) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + memberId));

        University universityEnum = University.fromString(universityName.trim());
        if (universityEnum == University.UNKNOWN_UNIVERSITY) {
            // 대학교가 알 수 없는 경우 처리
        }

        member.setUniversity(universityEnum);
        memberRepository.save(member);
    }

    /**
     * 대학교명 형식 검증
     */
    private boolean isValidUniversityName(String univName) {
        Pattern universityPattern = Pattern.compile(".*대학교$");
        return universityPattern.matcher(univName).matches();
    }

    /**
     * 약관 동의 처리
     */
    @Transactional
    public ApiResponse<RegisterDTO.TermsDTO> agreeTerms(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));
        member.setTermsAgreed(true);
        memberRepository.save(member);
        return ApiResponse.onSuccess(new RegisterDTO.TermsDTO(member.getId(), true));
    }

    /**
     * 회원가입 성공 정보
     */
    @Transactional(readOnly = true)
    public ApiResponse<RegisterDTO.SignupSuccessDTO> getSignupSuccessInfo(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다. memberId=" + memberId));

        RegisterDTO.SignupSuccessDTO successInfo = new RegisterDTO.SignupSuccessDTO(member.getId(), "회원가입이 성공적으로 완료되었습니다!");

        // ApiResponse에서 onSuccess 메서드를 사용하여 성공 응답 생성
        return ApiResponse.onSuccess(successInfo);
    }

}
