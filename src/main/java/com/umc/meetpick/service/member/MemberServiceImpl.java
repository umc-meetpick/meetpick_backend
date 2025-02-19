package com.umc.meetpick.service.member;

import com.umc.meetpick.common.exception.handler.GeneralHandler;
import com.umc.meetpick.common.response.status.ErrorCode;
import com.umc.meetpick.dto.*;
import com.umc.meetpick.entity.Major;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.MemberProfiles.MemberProfile;
import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.entity.SubMajor;
import com.umc.meetpick.enums.*;
import com.umc.meetpick.repository.MajorRepository;
import com.umc.meetpick.repository.SubMajorRepository;
import com.umc.meetpick.repository.member.MemberMappingRepository;
import com.umc.meetpick.repository.member.MemberProfileRepository;
import com.umc.meetpick.repository.member.MemberRepository;
import com.umc.meetpick.repository.member.MemberSecondProfileRepository;
import com.umc.meetpick.service.member.factory.MemberDetailTemplateFactory;
import com.umc.meetpick.service.member.template.GetMemberDetail;
import com.umc.meetpick.service.member.validator.MemberValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.umc.meetpick.service.member.factory.MemberDtoFactory.memberToProfileDto;
import static com.umc.meetpick.service.member.validator.MemberValidator.validateContact;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberSecondProfileRepository memberSecondProfileRepository;
    private final MemberRepository memberRepository;
    private final RestTemplate restTemplate;
    private static final String API_KEY = "c5efad4c-356f-4989-949f-cbb056439ba6";
    private static final String EMAIL_VERIFY_URL = "https://univcert.com/api/v1/certify";
    private static final String CODE_VERIFY_URL = "https://univcert.com/api/v1/certifycode";
    private static final String baseUrl = "https://hangeulbucket.s3.ap-northeast-2.amazonaws.com";
    private final MemberProfileRepository memberProfileRepository;
    private final SubMajorRepository subMajorRepository;
    private final MemberValidator memberValidator;
    private final MemberMappingRepository memberMappingRepository;
    private final MajorRepository majorRepository;

    @Override
    public Map<String, Object> getMemberDetail(Long memberSecondProfileId) {

        MemberSecondProfile memberSecondProfile = memberSecondProfileRepository.findById(memberSecondProfileId).orElseThrow(()-> new GeneralHandler(ErrorCode.PROFILE2_NOT_FOUND));

        MemberDetailTemplateFactory factory = new MemberDetailTemplateFactory();
        GetMemberDetail memberDetail = factory.getTemplate(memberSecondProfile.getMateType());

        return memberDetail.execute(memberSecondProfile);
    }

    @Transactional
    @Override
    public RegisterDTO.SignupSuccessDTO saveMember(Long memberId, RegisterDTO.SignUpDTO signUpDTO) {

            Member member = memberRepository.findMemberById(memberId);

            if(member.isVerified()){
                member.setMember(signUpDTO.getName(), signUpDTO.getGender(), new Date(signUpDTO.getBirthday().getTime()));
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

        SubMajor subMajor = subMajorRepository.findByName(signUpProfileDTO.getSubMajor()).orElseThrow(()-> new GeneralHandler(ErrorCode.SUBMAJOR_NOT_EXSIT));

        Set<Hobby> hobbies = signUpProfileDTO.getHobbyList()
                .stream()
                .map(Hobby::fromString)
                .collect(Collectors.toSet());

        // TODO 나중에 구조 바꾸기
            MemberProfile memberProfile = MemberProfile.builder()
                    .nickname(signUpProfileDTO.getNickName())
                    .profileImage(setImage(signUpProfileDTO.getImageNumber()))
                    .studentNumber(signUpProfileDTO.getStudentNumber())
                    .MBTI(signUpProfileDTO.getMbti())
                    .subMajor(subMajor)
                    .hobbies(hobbies)
                    .contact(ContactType.fromString(signUpProfileDTO.getContactType()))
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
            return "중복된 닉네임 입니다.";
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

    @Override
    public MyProfileDto getMyProfile(Long memberId) {

        log.info("Service : getMyProfile 호출 {}", memberId);

        Member member = memberRepository.findMemberById(memberId);

        return memberToProfileDto(member);
    }

    // TODO 리팩토링 필요
    @Override
    public ContactResponseDto getContactInfo(Long memberId, Long requestId) {

        // memberId로 회원 조회
        MemberSecondProfile memberSecondProfile = memberSecondProfileRepository.findMemberSecondProfileById(requestId)
                .orElseThrow(()-> new GeneralHandler(ErrorCode.PROFILE2_NOT_FOUND));

        Member member = memberSecondProfile.getMember();

        if(member == null){
            throw new GeneralHandler(ErrorCode.PROFILE2_MEMBER_MISMATCH);
        }

        validateContact(member);

        MemberProfile memberProfile = member.getMemberProfile();

        // 회원 검증 (예: 권한 확인 등)
//        memberValidator.validateContact(member, mappingId);

        // mappingId로 MemberMapping을 조회한 후, 변환하여 ContactResponseDto로 반환.

        return ContactResponseDto.builder()
                .contactName(memberProfile.getContactInfo())
                .contactType(memberProfile.getContact().getKoreanName())
                .build();
    }

    private String setImage(int input){
        return switch (input) {
            case 1 -> baseUrl + "/default.png";
            case 2 -> baseUrl + "/hamburger.png";
            case 3 -> baseUrl + "/study.png";
            case 4 -> baseUrl + "/muffler.png";
            case 5 -> baseUrl + "/hoody.png";
            case 6 -> baseUrl + "/graduate.png";
            case 7 -> baseUrl + "/earphone.png";
            case 8 -> baseUrl + "/boxing.png";
            case 9 -> baseUrl + "/magician.png";
            default -> throw new GeneralHandler(ErrorCode._BAD_REQUEST);
        };
    }

    // TODO 나중에 리팩토링
    @Override
    public MajorDto.InfoDto getMajorList() {
        List<Major> majors = majorRepository.findAll();

        List<MajorDto.MajorInfoDto> majorInfoDtoList = majors.stream()
                .map(major -> {
                    List<MajorDto.SubMajorInfoDto> subMajorInfoDtos = subMajorRepository.findAllByMajor(major).stream()
                            .map(subMajor -> MajorDto.SubMajorInfoDto.builder()
                                    .subMajorId(subMajor.getId())
                                    .subMajorName(subMajor.getName())
                                    .build())
                            .collect(Collectors.toList());

                    return MajorDto.MajorInfoDto.builder()
                            .majorName(major.getName())
                            .subMajorInfoDtoList(subMajorInfoDtos)
                            .build();
                })
                .collect(Collectors.toList());

        return MajorDto.InfoDto.builder()
                .subMajors(majorInfoDtoList)
                .build();
    }


    /**
     * 대학교명 형식 검증
     */
    private boolean isValidUniversityName(String univName) {
        Pattern universityPattern = Pattern.compile(".*대학교$");
        return universityPattern.matcher(univName).matches();
    }

}
