package com.umc.meetpick.service.member;

import com.umc.meetpick.dto.ContactResponseDto;
import com.umc.meetpick.dto.MemberDetailResponseDto;
import com.umc.meetpick.dto.MyProfileDto;
import com.umc.meetpick.dto.RegisterDTO;

import java.util.Map;

public interface MemberService {

    // TODO 뜯어 고치기
    Map<String, Object> getMemberDetail(Long memberId);

    RegisterDTO.SignupSuccessDTO saveMember(Long memberId, RegisterDTO.SignUpDTO signUpDTO);

    RegisterDTO.SignupSuccessDTO saveMemberProfile(Long memberId, RegisterDTO.SignUpProfileDTO signUpProfileDTO);

    String sendVerificationCode(RegisterDTO.EmailVerificationRequestDTO requestDTO);

    String verifyEmailCode(Long memberId, RegisterDTO.EmailVerificationCodeDTO requestDTO);

    String nickDuplicate(Long memberId, String nickName);

    MyProfileDto getMyProfile(Long memberId);

    ContactResponseDto getContactInfo(Long memberId, Long mappingId);
}
