package com.umc.meetpick.service;

import com.umc.meetpick.common.response.ApiResponse;
import com.umc.meetpick.common.response.status.ErrorCode;
import com.umc.meetpick.common.response.status.SuccessCode;
import com.umc.meetpick.dto.ProfileDTO;
import com.umc.meetpick.entity.MemberProfiles.MemberProfile;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.Major;
import com.umc.meetpick.entity.SubMajor;
import com.umc.meetpick.enums.ContactType;
import com.umc.meetpick.enums.Hobby;
import com.umc.meetpick.enums.MBTI;
import com.umc.meetpick.repository.member.MemberRepository;
import com.umc.meetpick.repository.SubMajorRepository;
import com.umc.meetpick.repository.member.MemberProfileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileService {

    private final MemberRepository memberRepository;
    private final MemberProfileRepository memberProfileRepository;
    private final SubMajorRepository subMajorRepository;

    // 연락처 설정
    public ApiResponse<ProfileDTO.ContactDTO.ContactResponseDTO> setContact(ProfileDTO.ContactDTO.ContactRequestDTO contactRequestDTO) {
        Long memberId = contactRequestDTO.getMemberId();
        ContactType contactType = contactRequestDTO.getContactType();
        String contactInfo = contactRequestDTO.getContactInfo();

        boolean isValidContactType = false;
        for (ContactType type : ContactType.values()) {
            if (type == contactType) {
                isValidContactType = true;
                break;
            }
        }

        if (!isValidContactType) {
            return ApiResponse.ofFailure(ErrorCode.CONTACT_TYPE_INVALID, null);
        }

        if (contactInfo == null || contactInfo.isEmpty()) {
            return ApiResponse.ofFailure(ErrorCode.CONTACT_INFO_INVALID, null);
        }

        Optional<MemberProfile> optionalMemberProfile = memberProfileRepository.findByMemberId(memberId);
        if (optionalMemberProfile.isEmpty()) {
            return ApiResponse.ofFailure(ErrorCode.MEMBER_NOT_FOUND, null);
        }

        MemberProfile memberProfile = optionalMemberProfile.get();
        memberProfile.setContact(contactType);
        memberProfile.setContactInfo(contactInfo);
        memberProfileRepository.save(memberProfile);

        ProfileDTO.ContactDTO.ContactResponseDTO responseDTO = new ProfileDTO.ContactDTO.ContactResponseDTO(
                memberProfile.getId(),
                memberProfile.getContact(),
                memberProfile.getContactInfo()
        );

        return ApiResponse.onSuccess(responseDTO);
    }

    // 취미 설정
    public ApiResponse<ProfileDTO.HobbyDTO.HobbyResponseDTO> setHobbies(ProfileDTO.HobbyDTO.HobbyRequestDTO hobbyRequestDTO) {
        Long memberId = hobbyRequestDTO.getMemberId();
        Set<Integer> hobbyIds = hobbyRequestDTO.getHobbyIds();

        if (hobbyIds.size() > 5) {
            return ApiResponse.ofFailure(ErrorCode.HOBBY_SELECTION_ERROR, null);
        }

        Optional<MemberProfile> optionalMemberProfile = memberProfileRepository.findByMemberId(memberId);
        if (optionalMemberProfile.isEmpty()) {
            return ApiResponse.ofFailure(ErrorCode.MEMBER_NOT_FOUND, null);
        }

        MemberProfile memberProfile = optionalMemberProfile.get();
        Set<Hobby> selectedHobbies = hobbyIds.stream()
                .map(id -> Hobby.values()[id - 1])
                .filter(hobby -> hobby != null)
                .collect(Collectors.toSet());

        memberProfile.setHobbies(selectedHobbies);
        memberProfileRepository.save(memberProfile);

        ProfileDTO.HobbyDTO.HobbyResponseDTO hobbyResponseDTO = new ProfileDTO.HobbyDTO.HobbyResponseDTO(
                memberProfile.getId(),
                selectedHobbies.stream()
                        .map(Hobby::getKoreanName)
                        .collect(Collectors.toSet())
        );

        return ApiResponse.onSuccess(hobbyResponseDTO);
    }

    // MBTI 설정
    @Transactional
    public ApiResponse<ProfileDTO.MBTIDTO.MBTIResponseDTO> setMBTI(Long memberId, ProfileDTO.MBTIDTO.MBTIRequestDTO requestDTO) {
        if (requestDTO == null || requestDTO.getMBTI() == null) {
            return ApiResponse.onFailure(
                    ErrorCode.INVALID_MBTI.getCode(),
                    "MBTI 값이 비어 있습니다.",
                    null
            );
        }

        String mbtiString = requestDTO.getMBTI().toUpperCase();
        MBTI mbtiEnum;
        try {
            mbtiEnum = MBTI.valueOf(mbtiString);
        } catch (IllegalArgumentException e) {
            return ApiResponse.onFailure(
                    ErrorCode.INVALID_MBTI.getCode(),
                    "유효하지 않은 MBTI 값입니다.",
                    null
            );
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("❌ 해당 ID의 Member를 찾을 수 없습니다."));

        MemberProfile memberProfile = memberProfileRepository.findByMember(member)
                .orElseGet(() -> {
                    MemberProfile newProfile = MemberProfile.builder()
                            .member(member)
                            .nickname("Default Nickname")
                            .profileImage("default.png")
                            .studentNumber(0)
                            .MBTI(mbtiEnum)
                            .build();
                    memberProfileRepository.save(newProfile);
                    return newProfile;
                });

        memberProfile.setMBTI(mbtiEnum);
        memberProfileRepository.save(memberProfile);

        return ApiResponse.of(SuccessCode.MBTI_SET_SUCCESS,
                new ProfileDTO.MBTIDTO.MBTIResponseDTO(memberId, memberProfile.getId(), mbtiEnum.name(), mbtiEnum.name() + " 메이트이시군요!"));
    }

    // 전공 설정
    @Transactional
    public ApiResponse<ProfileDTO.MajorDTO.MajorResponseDTO> setMajor(Long memberId, ProfileDTO.MajorDTO.MajorRequestDTO requestDTO) {
        Long subMajorId = requestDTO.getSubMajorId();

        SubMajor subMajor = subMajorRepository.findById(subMajorId)
                .orElseThrow(() -> new RuntimeException(ErrorCode.SUB_MAJOR_NOT_FOUND.getMessage()));

        Major major = subMajor.getMajor();

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException(ErrorCode.MEMBER_NOT_FOUND.getMessage()));

        MemberProfile memberProfile = member.getMemberProfile();
        if (memberProfile == null) {
            throw new RuntimeException("❌ MemberProfile이 존재하지 않습니다.");
        }

        memberProfile.setMajor(major);
        memberProfileRepository.save(memberProfile);

        return ApiResponse.of(SuccessCode.MAJOR_SET_SUCCESS, new ProfileDTO.MajorDTO.MajorResponseDTO(memberId, subMajor.getId(), subMajor.getName(), major.getId(), major.getName()));
    }

    // 프로필 이미지 설정
    @Transactional
    public ApiResponse<ProfileDTO.ProfileImageDTO.ProfileImageResponseDTO> setProfileImage(Long memberId, ProfileDTO.ProfileImageDTO.ProfileImageRequestDTO requestDTO) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("❌ 해당 ID의 Member를 찾을 수 없습니다."));

        MemberProfile memberProfile = memberProfileRepository.findByMember(member)
                .orElseGet(() -> {
                    MemberProfile newProfile = MemberProfile.builder()
                            .member(member)
                            .profileImage("default.png")
                            .build();
                    memberProfileRepository.save(newProfile);
                    return newProfile;
                });

        memberProfile.setProfileImage(requestDTO.getImageUrl());
        memberProfileRepository.save(memberProfile);

        return ApiResponse.onSuccess(new ProfileDTO.ProfileImageDTO.ProfileImageResponseDTO(memberId, memberProfile.getId(), memberProfile.getProfileImage()));
    }

    // 닉네임 중복 검사
    public ApiResponse<ProfileDTO.NicknameDTO.NicknameCheckResponseDTO> checkNicknameAvailability(Long memberId, String nickname) {
        boolean exists = memberProfileRepository.existsByNickname(nickname);
        boolean isAvailable = !exists || (memberId != null && memberProfileRepository.findByMemberId(memberId)
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
    public ApiResponse<ProfileDTO.NicknameDTO.NicknameResponseDTO> setNickname(Long memberId, ProfileDTO.NicknameDTO.NicknameRequestDTO requestDTO) {
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

        // Member 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("❌ 해당 ID의 Member를 찾을 수 없습니다."));

        // MemberProfile 조회 (없으면 새로 생성)
        MemberProfile memberProfile = memberProfileRepository.findByMemberId(memberId)
                .orElseGet(() -> {
                    MemberProfile newProfile = MemberProfile.builder()
                            .member(member)
                            .nickname(nickname)
                            .profileImage("default.png")
                            .build();
                    memberProfileRepository.save(newProfile);
                    return newProfile;
                });

        // 닉네임 업데이트
        memberProfile.setNickname(nickname);
        memberProfileRepository.save(memberProfile);

        // Member 테이블의 member_profile 컬럼 업데이트
        if (member.getMemberProfile() == null || !member.getMemberProfile().getId().equals(memberProfile.getId())) {
            member.setMemberProfile(memberProfile);
            memberRepository.save(member);
        }

        log.info("✅ 닉네임 설정 완료 - memberId={}, profileId={}, nickname={}", memberId, memberProfile.getId(), nickname);
        return ApiResponse.of(
                SuccessCode.NICKNAME_SET_SUCCESS,
                new ProfileDTO.NicknameDTO.NicknameResponseDTO(memberId, memberProfile.getId(), nickname)
        );
    }

    // 학번 설정
    @Transactional
    public ApiResponse<ProfileDTO.StudentNumberDTO.StudentNumberResponseDTO> setStudentNumber(Long memberId, ProfileDTO.StudentNumberDTO.StudentNumberRequestDTO requestDTO) {
        String studentNumberStr = requestDTO.getStudentNumber();
        log.info("🔍 학번 설정 요청 - memberId={}, studentNumber={}", memberId, studentNumberStr);

        // 숫자 검증 (추가)
        if (studentNumberStr == null || !studentNumberStr.matches("\\d+")) {
            return ApiResponse.onFailure(
                    ErrorCode.INVALID_STUDENT_NUMBER.getCode(),
                    ErrorCode.INVALID_STUDENT_NUMBER.getMessage(),
                    null
            );
        }

        int studentNumber = Integer.parseInt(studentNumberStr); // 숫자로 변환

        // Member 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("❌ 해당 ID의 Member를 찾을 수 없습니다."));

        // MemberProfile 조회 (없으면 새로 생성)
        MemberProfile memberProfile = memberProfileRepository.findByMember(member)
                .orElseGet(() -> {
                    MemberProfile newProfile = MemberProfile.builder()
                            .member(member)
                            .nickname("Default Nickname")
                            .profileImage("default.png")
                            .studentNumber(studentNumber)
                            .build();
                    memberProfileRepository.save(newProfile);
                    return newProfile;
                });

        // 학번 업데이트
        memberProfile.setStudentNumber(studentNumber);
        memberProfileRepository.save(memberProfile);

        // Member 테이블의 member_profile 컬럼 업데이트
        if (member.getMemberProfile() == null || !member.getMemberProfile().getId().equals(memberProfile.getId())) {
            member.setMemberProfile(memberProfile);
            memberRepository.save(member);
        }

        log.info("✅ 학번 설정 완료 - memberId={}, profileId={}, studentNumber={}", memberId, memberProfile.getId(), studentNumber);
        return ApiResponse.of(
                SuccessCode.STUDENT_NUMBER_SET_SUCCESS,
                new ProfileDTO.StudentNumberDTO.StudentNumberResponseDTO(memberId, memberProfile.getId(), studentNumber)
        );
    }
}
