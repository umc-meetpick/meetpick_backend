package com.umc.meetpick.service;

import com.umc.meetpick.common.exception.handler.GeneralHandler;
import com.umc.meetpick.common.response.status.ErrorCode;
import com.umc.meetpick.dto.MemberDetailResponseDto;
import com.umc.meetpick.dto.MemberResponseDTO;
import com.umc.meetpick.dto.RegisterDTO;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.MemberProfiles.MemberProfile;
import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.entity.SubMajor;
import com.umc.meetpick.enums.*;
import com.umc.meetpick.repository.SubMajorRepository;
import com.umc.meetpick.repository.member.MemberProfileRepository;
import com.umc.meetpick.repository.member.MemberRepository;
import com.umc.meetpick.repository.member.MemberSecondProfileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberSecondProfileRepository memberSecondProfileRepository;
    private final MemberRepository memberRepository;
    private final RestTemplate restTemplate;
    private static final String API_KEY = "c5efad4c-356f-4989-949f-cbb056439ba6";
    private static final String EMAIL_VERIFY_URL = "https://univcert.com/api/v1/certify";
    private static final String CODE_VERIFY_URL = "https://univcert.com/api/v1/certifycode";
    private final MemberProfileRepository memberProfileRepository;
    private final SubMajorRepository subMajorRepository;

    @Override
    public MemberDetailResponseDto getMemberDetail(Long memberId) {
        Member member = memberRepository.findMemberById(memberId);

        // TODO mate 종류마다 달리하기 + 시간 정보
        return MemberDetailResponseDto.builder()
                .userId(memberId)
                .age(member.getAge())
                .studentNumber(member.getMemberProfile().getStudentNumber())
                .gender(member.getGender().getKoreanName())
                .mbti(member.getMemberProfile().getMBTI())
                .major(member.getMemberProfile().getSubMajor().getName())
                .hobbies(member.getMemberProfile().getHobbies().stream().map(Hobby::getKoreanName).collect(Collectors.toSet()))
                .foodTypes(member.getMemberSecondProfile().getFoodTypes().stream().map(FoodType::getKoreanName).collect(Collectors.toSet()))
                .comment(member.getMemberSecondProfile().getComment())
                .build();
    }

    @Transactional
    @Override
    public RegisterDTO.SignupSuccessDTO saveMember(Long memberId, RegisterDTO.SignUpDTO signUpDTO) {

            Member member = memberRepository.findMemberById(memberId);

            if(member.isVerified()){
                member.setMember(signUpDTO.getName(), signUpDTO.getGender(), signUpDTO.getBirthday());
                memberRepository.save(member);
            } else {
                throw new GeneralHandler(ErrorCode._BAD_REQUEST);
            }

            return RegisterDTO.SignupSuccessDTO.builder()
                    .memberId(member.getId())
                    .build();

    }

    @Override
    public RegisterDTO.SignupSuccessDTO saveMemberProfile(Long memberId, RegisterDTO.SignUpProfileDTO signUpProfileDTO) {

        //TODO 유효성 검사 및 이미지 처리 로직 구현

        SubMajor subMajor = subMajorRepository.findByName(signUpProfileDTO.getName()).orElseThrow(()-> new GeneralHandler(ErrorCode.SUBMAJOR_NOT_EXSIT));

        Set<Hobby> hobbies = signUpProfileDTO.getHobbyList()
                .stream()
                .map(hobby -> Hobby.fromString(hobby.getKoreanName()))
                .collect(Collectors.toSet());

            MemberProfile memberProfile = MemberProfile.builder()
                    .nickname(signUpProfileDTO.getName())
                    .profileImage(null)
                    .studentNumber(signUpProfileDTO.getStudentNumber())
                    .MBTI(signUpProfileDTO.getMbti())
                    .subMajor(subMajor)
                    .hobbies(hobbies)
                    .contact(signUpProfileDTO.getContactType())
                    .contactInfo(signUpProfileDTO.getContactInfo())
                    .build();

            memberProfileRepository.save(memberProfile);

            // TODO 리팩터링!!
            Member member = memberRepository.findMemberById(memberId);
            member.setMemberProfile(memberProfile);
            memberRepository.save(member);

            return RegisterDTO.SignupSuccessDTO.builder().memberId(memberId).build();
    }

    //TODO 리팩터링 필요
    public String sendVerificationCode(RegisterDTO.EmailVerificationRequestDTO requestDTO) {

        if (!isValidUniversityName(requestDTO.getUnivName())) {
            throw new GeneralHandler(ErrorCode.INVALID_UNIVERSITY);
        }

        // TODO 함수화 혹은 webclient
        // TODO 이건 그냥 만들어진 api 쓰면 됨. 사이트 참고
        // TODO 메시지 보내는 형식 바꾸기
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject requestBody = new JSONObject();
        requestBody.put("key", API_KEY);
        requestBody.put("email", requestDTO.getEmail());
        requestBody.put("univName", requestDTO.getUnivName());
        requestBody.put("univ_check", true);

        HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers);
        ResponseEntity<String> response;

        response = restTemplate.postForEntity(EMAIL_VERIFY_URL, request, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return "인증 코드가 전송되었습니다.";
        } else {
            throw new GeneralHandler(ErrorCode._INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 이메일 인증 코드 검증
     */
    @Transactional
    public String verifyEmailCode(Long memberId, RegisterDTO.EmailVerificationCodeDTO requestDTO) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject requestBody = new JSONObject();
        requestBody.put("key", API_KEY);
        requestBody.put("email", requestDTO.getEmail());
        requestBody.put("univName", requestDTO.getUnivName());
        requestBody.put("code", requestDTO.getVerificationCode());

        // TODO 인증 완료된 유저를 따로 저장해둬야 할 듯?

        HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers);
        ResponseEntity<String> response;

        response = restTemplate.postForEntity(CODE_VERIFY_URL, request, String.class);

        JSONObject responseBody = new JSONObject(response.getBody());
        boolean isSuccess = responseBody.getBoolean("success");

        if (response.getStatusCode().is2xxSuccessful() && isSuccess) {
            updateUniversity(memberId, requestDTO.getUnivName());
            return "인증 성공, 대학교 정보가 저장되었습니다.";
        } else {
            // TODO 코드 만들기
            throw new GeneralHandler(ErrorCode._BAD_REQUEST);
        }
    }

    @Override
    public String nickDuplicate(Long memberId, String nickName) {

        if(memberProfileRepository.findByNickname(nickName).isPresent()){
            throw new GeneralHandler(ErrorCode.NICKNAME_DUPLICATE);
        }

        return "사용 가능한 닉네임입니다";
    }

    /**
     * 대학교명 업데이트
     */
    @Transactional
    public void updateUniversity(Long memberId, String universityName) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + memberId));

        University universityEnum = University.fromString(universityName.trim());

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

}
