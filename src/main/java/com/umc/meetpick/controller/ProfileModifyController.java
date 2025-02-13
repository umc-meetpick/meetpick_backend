package com.umc.meetpick.controller;

import com.umc.meetpick.common.response.ApiResponse;
import com.umc.meetpick.dto.ProfileDTO;
import com.umc.meetpick.dto.ProfileModifyDTO;
import com.umc.meetpick.service.CurrentUser;
import com.umc.meetpick.service.ProfileModifyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Tag(name="프로필 수정 관련 API", description = "사용자 프로필 수정 API 입니다.")
@RestController
@RequestMapping("/api/modify")
@RequiredArgsConstructor
@Slf4j
public class ProfileModifyController {

    private final ProfileModifyService profileModifyService;

    // 사용자의 연락처 변경 API
    @Operation(summary = "사용자의 연락처 변경 API", description = "회원 ID와 연락처 유형 및 정보를 바탕으로 연락처를 변경합니다.")
    @PostMapping("/contact/set")
    public ApiResponse<ProfileModifyDTO.ContactDTO.ContactResponseDTO> modifyContact(
            @CurrentUser Long memberId,
            @RequestBody ProfileModifyDTO.ContactDTO.ContactRequestDTO contactRequestDTO) {
        contactRequestDTO.setMemberId(memberId);
        log.info("📩 연락처 설정 요청 - memberId={}, contactType={}, contactInfo={}",
                memberId, contactRequestDTO.getContactType(), contactRequestDTO.getContactInfo());
        return profileModifyService.modifyContact(contactRequestDTO);
    }

    // 취미 변경 API
    @Operation(summary = "사용자의 취미 변경 API", description = "회원 ID와 선택한 취미 목록을 바탕으로 취미를 변경합니다.")
    @PostMapping("/hobby/set")
    public ApiResponse<ProfileModifyDTO.HobbyDTO.HobbyResponseDTO> modifyHobbies(
            @CurrentUser Long memberId,
            @RequestBody ProfileModifyDTO.HobbyDTO.HobbyRequestDTO hobbyRequestDTO) {
        hobbyRequestDTO.setMemberId(memberId);
        return profileModifyService.modifyHobbies(hobbyRequestDTO);
    }

    // MBTI 변경 API
    @Operation(summary = "MBTI 변경", description = "회원의 MBTI를 변경합니다.")
    @PostMapping("/mbti/set")
    public ApiResponse<ProfileModifyDTO.MBTIDTO.MBTIResponseDTO> modifyMBTI(
            @CurrentUser Long memberId,
            @RequestBody ProfileModifyDTO.MBTIDTO.MBTIRequestDTO requestDTO) {
        log.info("🧠 MBTI 설정 요청 - memberId={}, MBTI={}", memberId, requestDTO.getMBTI());
        return profileModifyService.modifyMBTI(memberId, requestDTO);
    }

    // 전공 변경 API
    @Operation(summary = "전공 변경", description = "회원의 전공을 변경합니다.")
    @PostMapping("/major/set")
    public ApiResponse<ProfileModifyDTO.MajorDTO.MajorResponseDTO> modifyMajor(
            @CurrentUser Long memberId,
            @RequestBody ProfileModifyDTO.MajorDTO.MajorRequestDTO requestDTO) {
        log.info("🎓 전공 설정 요청 - memberId={}, subMajorId={}", memberId, requestDTO.getSubMajorId());
        return profileModifyService.modifyMajor(memberId, requestDTO);
    }

    // 프로필 이미지 변경 API
    @Operation(summary = "프로필 이미지 변경", description = "회원의 프로필 이미지를 변경합니다.")
    @PostMapping("/image/set")
    public ApiResponse<ProfileModifyDTO.ProfileImageDTO.ProfileImageResponseDTO> modifyProfileImage(
            @CurrentUser Long memberId,
            @RequestBody ProfileModifyDTO.ProfileImageDTO.ProfileImageRequestDTO requestDTO) {
        log.info("🖼️ 프로필 이미지 설정 요청 - memberId={}, imageUrl={}", memberId, requestDTO.getImageUrl());
        return profileModifyService.modifyProfileImage(memberId, requestDTO);
    }

    // 닉네임 중복 검사 API
    @Operation(summary = "닉네임 중복 검사", description = "닉네임 중복을 검사합니다.")
    @GetMapping("/nickname/check")
    public ApiResponse<ProfileModifyDTO.NicknameDTO.NicknameCheckResponseDTO> checkNickname(
            @CurrentUser Long memberId,
            @RequestParam("nickname") String nickname) {
        log.info("✅ 닉네임 중복 검사 - memberId={}, nickname={}", memberId, nickname);
        return profileModifyService.checkNicknameAvailability(memberId, nickname);
    }

    // 닉네임 변경 API
    @Operation(summary = "닉네임 변경", description = "회원의 닉네임을 변경합니다.")
    @PostMapping("/nickname/set")
    public ApiResponse<ProfileModifyDTO.NicknameDTO.NicknameResponseDTO> modifyNickname(
            @CurrentUser Long memberId,
            @RequestBody ProfileModifyDTO.NicknameDTO.NicknameRequestDTO requestDTO) {
        log.info("✅ 닉네임 설정 요청 - memberId={}, nickname={}", memberId, requestDTO.getNickname());
        return profileModifyService.modifyNickname(memberId, requestDTO);
    }
    // 학번 변경 API
    @Operation(summary = "학번 변경", description = "회원의 학번을 변경합니다.")
    @PostMapping("/student-number/set")
    public ApiResponse<ProfileModifyDTO.StudentNumberDTO.StudentNumberResponseDTO> modifyStudentNumber(
            @CurrentUser Long memberId,
            @Valid @RequestBody ProfileModifyDTO.StudentNumberDTO.StudentNumberRequestDTO requestDTO) {
        log.info("📚 학번 설정 요청 - memberId={}, studentNumber={}", memberId, requestDTO.getStudentNumber());
        return profileModifyService.modifyStudentNumber(memberId, requestDTO);
    }
}