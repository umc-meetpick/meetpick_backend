package com.umc.meetpick.service.modify;

import com.umc.meetpick.common.exception.handler.GeneralHandler;
import com.umc.meetpick.common.response.status.ErrorCode;
import com.umc.meetpick.common.response.status.SuccessCode;
import com.umc.meetpick.dto.ProfileDTO;
import com.umc.meetpick.common.response.ApiResponse;
import com.umc.meetpick.entity.Major;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.MemberProfiles.MemberProfile;
import com.umc.meetpick.entity.SubMajor;
import com.umc.meetpick.enums.ContactType;
import com.umc.meetpick.enums.Hobby;
import com.umc.meetpick.enums.MBTI;
import com.umc.meetpick.repository.SubMajorRepository;
import com.umc.meetpick.repository.member.MemberProfileRepository;
import com.umc.meetpick.repository.member.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProfileModifyService {
    private final MemberRepository memberRepository;
    private final MemberProfileRepository memberProfileRepository;
    private final SubMajorRepository subMajorRepository;

    // 연락처 수정
    public ApiResponse<ProfileDTO.ContactDTO.ContactResponseDTO> modifyContact(Long memberId, ProfileDTO.ContactDTO.ContactRequestDTO contactRequestDTO) {
        ContactType contactType = contactRequestDTO.getContactType();
        String contactInfo = contactRequestDTO.getContactInfo();

        // 연락처 유형 검증
        if (Arrays.stream(ContactType.values()).noneMatch(type -> type == contactType)) {
            return ApiResponse.ofFailure(ErrorCode.CONTACT_TYPE_INVALID, null);
        }

        if (contactInfo == null || contactInfo.isEmpty()) {
            return ApiResponse.ofFailure(ErrorCode.CONTACT_INFO_INVALID, null);
        }

        // 프로필 조회
        MemberProfile memberProfile = getMemberProfileOrThrow(memberId);

        // 연락처 정보 업데이트
        memberProfile.setContact(contactType);
        memberProfile.setContactInfo(contactInfo);
        memberProfileRepository.save(memberProfile);

        // 응답 생성
        ProfileDTO.ContactDTO.ContactResponseDTO responseDTO = new ProfileDTO.ContactDTO.ContactResponseDTO(
                memberProfile.getId(),
                memberProfile.getContact(),
                memberProfile.getContactInfo()
        );
        return ApiResponse.onSuccess(responseDTO);
    }


    // 취미 수정
    public ApiResponse<ProfileDTO.HobbyDTO.HobbyResponseDTO> modifyHobbies(Long memberId, ProfileDTO.HobbyDTO.HobbyRequestDTO hobbyRequestDTO) {
        Set<String> hobbyNames = hobbyRequestDTO.getHobbyNames();

        // 취미 최대 선택 개수 검증 (최대 5개)
        if (hobbyNames.size() > 5) {
            return ApiResponse.ofFailure(ErrorCode.HOBBY_SELECTION_ERROR, null);
        }

        // 프로필 조회
        MemberProfile memberProfile = getMemberProfileOrThrow(memberId);

        // 선택된 취미 변환 (유효성 검사 포함)
        Set<Hobby> selectedHobbies;
        try {
            selectedHobbies = hobbyNames.stream()
                    .map(Hobby::fromString) // String -> Hobby Enum 변환
                    .collect(Collectors.toSet());
        } catch (GeneralHandler e) {
            return ApiResponse.ofFailure(ErrorCode.INVALID_ENUM, null); // 존재하지 않는 취미 입력 시 오류 반환
        }

        // 프로필에 취미 설정 후 저장
        memberProfile.setHobbies(selectedHobbies);
        memberProfileRepository.save(memberProfile);

        // 응답 DTO 생성 및 반환
        ProfileDTO.HobbyDTO.HobbyResponseDTO hobbyResponseDTO = new ProfileDTO.HobbyDTO.HobbyResponseDTO(
                memberProfile.getId(),
                selectedHobbies.stream()
                        .map(Hobby::getKoreanName) // 한글 이름 변환
                        .collect(Collectors.toSet())
        );

        return ApiResponse.onSuccess(hobbyResponseDTO);
    }



    // MBTI 수정
    @Transactional
    public ApiResponse<ProfileDTO.MBTIDTO.MBTIResponseDTO> modifyMBTI(Long memberId, ProfileDTO.MBTIDTO.MBTIRequestDTO requestDTO) {
        // 요청 값 검증
        if (requestDTO == null || requestDTO.getMBTI() == null) {
            return ApiResponse.onFailure(ErrorCode.INVALID_MBTI.getCode(), "MBTI 값이 비어 있습니다.", null);
        }

        // MBTI 문자열 변환 및 유효성 검사
        String mbtiString = requestDTO.getMBTI().toUpperCase();
        MBTI mbtiEnum;
        try {
            mbtiEnum = MBTI.valueOf(mbtiString);
        } catch (IllegalArgumentException e) {
            return ApiResponse.onFailure(ErrorCode.INVALID_MBTI.getCode(), "유효하지 않은 MBTI 값입니다.", null);
        }
        log.info("memberId ----------------------" + memberId );
        // 프로필 조회 (없으면 PROFILE_NOT_FOUND 반환)
        MemberProfile memberProfile = getMemberProfileOrThrow(memberId);

        // MBTI 업데이트
        memberProfile.setMBTI(mbtiEnum);
        memberProfileRepository.save(memberProfile);

        return ApiResponse.of(SuccessCode.MBTI_SET_SUCCESS,
                new ProfileDTO.MBTIDTO.MBTIResponseDTO(memberId, memberProfile.getId(), mbtiEnum.name(), mbtiEnum.name() + " 메이트이시군요!"));
    }


    // 전공 수정
    @Transactional
    public ApiResponse<ProfileDTO.MajorDTO.MajorResponseDTO> modifyMajor(Long memberId, ProfileDTO.MajorDTO.MajorRequestDTO requestDTO) {
        Long subMajorId = requestDTO.getSubMajorId();
        log.info("🎓 전공 수정 요청 - memberId={}, subMajorId={}", memberId, subMajorId);

        // 1. 서브전공 조회 (없으면 SUB_MAJOR_NOT_FOUND 반환)
        SubMajor subMajor = subMajorRepository.findById(subMajorId)
                .orElseThrow(() -> new RuntimeException(ErrorCode.SUB_MAJOR_NOT_FOUND.getMessage()));

        Major major = subMajor.getMajor();
        log.info("✅ subMajorId={} → majorId={}, majorName={}, subMajorName={}",
                subMajorId, major.getId(), major.getName(), subMajor.getName());

        // 2. 회원 프로필 조회 (없으면 PROFILE_NOT_FOUND 반환)
        MemberProfile memberProfile = getMemberProfileOrThrow(memberId);

        log.info("📝 기존 전공 정보 - memberProfileId={}, 기존 major={}, 기존 subMajor={}",
                memberProfile.getId(),
                (memberProfile.getMajor() != null ? memberProfile.getMajor().getName() : "없음"),
                (memberProfile.getSubMajor() != null ? memberProfile.getSubMajor().getName() : "없음"));

        // 3. 전공 변경 및 즉시 반영
        memberProfile.setMajor(major);
        memberProfile.setSubMajor(subMajor);
        memberProfileRepository.saveAndFlush(memberProfile);

        log.info("🔍 최종 반영 확인 - memberProfileId={}, 저장된 major={}, 저장된 subMajor={}",
                memberProfile.getId(),
                (memberProfile.getMajor() != null ? memberProfile.getMajor().getName() : "없음"),
                (memberProfile.getSubMajor() != null ? memberProfile.getSubMajor().getName() : "없음"));

        return ApiResponse.of(SuccessCode.MAJOR_SET_SUCCESS, new ProfileDTO.MajorDTO.MajorResponseDTO(
                memberId, subMajor.getId(), subMajor.getName(), memberProfile.getMajor().getId(), memberProfile.getMajor().getName()
        ));
    }

    // 프로필 이미지 설정
    @Transactional
    public ApiResponse<ProfileDTO.ProfileImageDTO.ProfileImageResponseDTO> modifyProfileImage(Long memberId, ProfileDTO.ProfileImageDTO.ProfileImageRequestDTO requestDTO) {
        log.info("🖼️ 프로필 이미지 설정 요청 - memberId={}, imageUrl={}", memberId, requestDTO.getImageUrl());

        // 프로필 조회 (없으면 PROFILE_NOT_FOUND 반환)
        MemberProfile memberProfile = getMemberProfileOrThrow(memberId);

        // 프로필 이미지 업데이트
        memberProfile.setProfileImage(requestDTO.getImageUrl());
        memberProfileRepository.save(memberProfile);

        return ApiResponse.onSuccess(new ProfileDTO.ProfileImageDTO.ProfileImageResponseDTO(memberId, memberProfile.getId(), memberProfile.getProfileImage()));
    }

    // 닉네임 중복 검사
    public ApiResponse<ProfileDTO.NicknameDTO.NicknameCheckResponseDTO> checkNicknameAvailability(Long memberId, String nickname) {
        boolean exists = memberProfileRepository.existsByNickname(nickname);
        boolean isAvailable = !exists || (memberId != null && memberRepository.findById(memberId)
                .map(Member::getMemberProfile)  // Member가 직접 Profile을 가지는 경우
                .map(profile -> profile.getNickname().equals(nickname))
                .orElse(false));


        if (!isAvailable) {
            return ApiResponse.onFailure(
                    ErrorCode.NICKNAME_ALREADY_EXISTS.getCode(),
                    ErrorCode.NICKNAME_ALREADY_EXISTS.getMessage(),
                    ProfileDTO.NicknameDTO.NicknameCheckResponseDTO.builder()
                            .isAvailable(false)
                            .build()
            );
        }

        return ApiResponse.of(
                SuccessCode.NICKNAME_AVAILABLE,
                ProfileDTO.NicknameDTO.NicknameCheckResponseDTO.builder()
                        .isAvailable(true)
                        .build()
        );
    }

    // 닉네임 설정
    @Transactional
    public ApiResponse<ProfileDTO.NicknameDTO.NicknameResponseDTO> modifyNickname(Long memberId, ProfileDTO.NicknameDTO.NicknameRequestDTO requestDTO) {
        String nickname = requestDTO.getNickname();
        log.info("🔍 닉네임 설정 요청 - memberId={}, nickname={}", memberId, nickname);

        // 중복 닉네임 검사
        if (memberProfileRepository.existsByNickname(nickname)) {
            return ApiResponse.onFailure(
                    ErrorCode.NICKNAME_ALREADY_EXISTS.getCode(),
                    ErrorCode.NICKNAME_ALREADY_EXISTS.getMessage(),
                    null
            );
        }

        // 프로필 조회 (없으면 PROFILE_NOT_FOUND 반환)
        MemberProfile memberProfile = getMemberProfileOrThrow(memberId);

        // 닉네임 업데이트
        memberProfile.setNickname(nickname);
        memberProfileRepository.save(memberProfile);

        log.info("✅ 닉네임 설정 완료 - memberId={}, profileId={}, nickname={}", memberId, memberProfile.getId(), nickname);
        return ApiResponse.of(
                SuccessCode.NICKNAME_SET_SUCCESS,
                new ProfileDTO.NicknameDTO.NicknameResponseDTO(memberId, memberProfile.getId(), nickname)
        );
    }


    // 학번 변경
    @Transactional
    public ApiResponse<ProfileDTO.StudentNumberDTO.StudentNumberResponseDTO> modifyStudentNumber(Long memberId, ProfileDTO.StudentNumberDTO.StudentNumberRequestDTO requestDTO) {
        String studentNumberStr = requestDTO.getStudentNumber();
        log.info("🔍 학번 설정 요청 - memberId={}, studentNumber={}", memberId, studentNumberStr);

        // 숫자 검증
        if (studentNumberStr == null || !studentNumberStr.matches("\\d+")) {
            return ApiResponse.onFailure(
                    ErrorCode.INVALID_STUDENT_NUMBER.getCode(),
                    ErrorCode.INVALID_STUDENT_NUMBER.getMessage(),
                    null
            );
        }

        int studentNumber = Integer.parseInt(studentNumberStr); // 숫자로 변환

        // 프로필 조회 (없으면 PROFILE_NOT_FOUND 반환)
        MemberProfile memberProfile = getMemberProfileOrThrow(memberId);

        // 학번 업데이트
        memberProfile.setStudentNumber(studentNumber);
        memberProfileRepository.save(memberProfile);

        log.info("✅ 학번 설정 완료 - memberId={}, profileId={}, studentNumber={}", memberId, memberProfile.getId(), studentNumber);
        return ApiResponse.of(
                SuccessCode.STUDENT_NUMBER_SET_SUCCESS,
                new ProfileDTO.StudentNumberDTO.StudentNumberResponseDTO(memberId, memberProfile.getId(), studentNumber)
        );
    }

    private MemberProfile getMemberProfileOrThrow(Long memberProfileId) {
        return memberProfileRepository.findById(memberProfileId)
                .orElseThrow(() -> new RuntimeException(ErrorCode.PROFILE_NOT_FOUND.getMessage()));
    }

}
