//package com.umc.meetpick.controller;
//
//import com.umc.meetpick.common.response.ApiResponse;
//import com.umc.meetpick.dto.*;
//import com.umc.meetpick.service.CurrentUser;
//import com.umc.meetpick.service.ProfileService;  // ProfileService로 통합
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.*;
//
//@Tag(name = "프로필 관련 API", description = "사용자의 프로필 정보를 설정하는 API입니다")
//@RestController
//@RequestMapping("/api/")
//@RequiredArgsConstructor
//@Slf4j
//public class ProfileController {
//
//    private final ProfileService profileService;  // ProfileService를 주입
//
//    // 취미 설정 API
//    @Operation(summary = "사용자의 취미 설정 API", description = "회원 ID와 선택한 취미 목록을 바탕으로 취미를 설정합니다.")
//    @PostMapping("/hobby/set")
//    public ApiResponse<ProfileDTO.HobbyDTO.HobbyResponseDTO> setHobbies(
//            @RequestParam Long memberId,
//            @CurrentUser Long currentUserId,
//            @RequestBody ProfileDTO.HobbyDTO.HobbyRequestDTO hobbyRequestDTO) {
//        hobbyRequestDTO.setMemberId(memberId);
//        return profileService.setHobbies(hobbyRequestDTO);  // ProfileService로 호출
//    }
//
//    // 전공 설정 API
//    @Operation(summary = "전공 설정", description = "회원의 전공을 설정합니다.")
//    @PostMapping("/major/set")
//    public ApiResponse<ProfileDTO.MajorDTO.MajorResponseDTO> setMajor(
//            @CurrentUser Long memberId,
//            @RequestBody ProfileDTO.MajorDTO.MajorRequestDTO requestDTO) {
//        log.info("🎓 전공 설정 요청 - memberId={}, subMajorId={}", memberId, requestDTO.getSubMajorId());
//        return profileService.setMajor(memberId, requestDTO);  // ProfileService로 호출
//    }
//
//    // MBTI 설정 API
//    @Operation(summary = "MBTI 설정", description = "회원의 MBTI를 설정합니다.")
//    @PostMapping("/mbti/set")
//    public ApiResponse<ProfileDTO.MBTIDTO.MBTIResponseDTO> setMBTI(
//            @CurrentUser Long memberId,
//            @RequestBody ProfileDTO.MBTIDTO.MBTIRequestDTO requestDTO) {
//        log.info("🧠 MBTI 설정 요청 - memberId={}, MBTI={}", memberId, requestDTO.getMBTI());
//        return profileService.setMBTI(memberId, requestDTO);  // ProfileService로 호출
//    }
//
//    // 닉네임 중복 검사 API
//    @Operation(summary = "닉네임 중복 검사", description = "닉네임 중복을 검사합니다.")
//    @GetMapping("/nickname/check")
//    public ApiResponse<ProfileDTO.NicknameDTO.NicknameCheckResponseDTO> checkNickname(
//            @CurrentUser Long memberId,
//            @RequestParam("nickname") String nickname) {
//        log.info("✅ 닉네임 중복 검사 - memberId={}, nickname={}", memberId, nickname);
//        return profileService.checkNicknameAvailability(memberId, nickname);  // ProfileService로 호출
//    }
//
//    // 닉네임 설정 API
//    @Operation(summary = "닉네임 설정", description = "회원의 닉네임을 설정합니다.")
//    @PostMapping("/nickname/set")
//    public ApiResponse<ProfileDTO.NicknameDTO.NicknameResponseDTO> setNickname(
//            @CurrentUser Long memberId,
//            @RequestBody ProfileDTO.NicknameDTO.NicknameRequestDTO requestDTO) {
//        log.info("✅ 닉네임 설정 요청 - memberId={}, nickname={}", memberId, requestDTO.getNickname());
//        return profileService.setNickname(memberId, requestDTO);  // ProfileService로 호출
//    }
//
//    // 프로필 이미지 설정 API
//    @Operation(summary = "프로필 이미지 설정", description = "회원의 프로필 이미지를 설정합니다.")
//    @PostMapping("/image")
//    public ApiResponse<ProfileDTO.ProfileImageDTO.ProfileImageResponseDTO> setProfileImage(
//            @CurrentUser Long memberId,
//            @RequestBody ProfileDTO.ProfileImageDTO.ProfileImageRequestDTO requestDTO) {
//        log.info("🖼️ 프로필 이미지 설정 요청 - memberId={}, imageUrl={}", memberId, requestDTO.getImageUrl());
//        return profileService.setProfileImage(memberId, requestDTO);  // ProfileService로 호출
//    }
//
//    // 학번 설정 API
//    @Operation(summary = "학번 설정", description = "회원의 학번을 설정합니다.")
//    @PostMapping("/student-number/set")
//    public ApiResponse<ProfileDTO.StudentNumberDTO.StudentNumberResponseDTO> setStudentNumber(
//            @CurrentUser Long memberId,
//            @RequestBody ProfileDTO.StudentNumberDTO.StudentNumberRequestDTO requestDTO) {
//        log.info("📚 학번 설정 요청 - memberId={}, studentNumber={}", memberId, requestDTO.getStudentNumber());
//        return profileService.setStudentNumber(memberId, requestDTO);  // ProfileService로 호출
//    }
//
//    // 사용자의 연락처 설정 API
//    @Operation(summary = "사용자의 연락처 설정 API", description = "회원 ID와 연락처 유형 및 정보를 바탕으로 연락처를 설정합니다.")
//    @PostMapping("/contact/set")
//    public ApiResponse<ProfileDTO.ContactDTO.ContactResponseDTO> setContact(
//            @CurrentUser Long memberId,
//            @RequestBody ProfileDTO.ContactDTO.ContactRequestDTO contactRequestDTO) {
//
//        contactRequestDTO.setMemberId(memberId);
//        return profileService.setContact(contactRequestDTO);  // ProfileService로 호출
//    }
//}
