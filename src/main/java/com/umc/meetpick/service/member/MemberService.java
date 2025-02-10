package com.umc.meetpick.service.member;

import com.umc.meetpick.dto.MemberDetailResponseDto;
import com.umc.meetpick.dto.MyProfileDto;
import com.umc.meetpick.dto.RegisterDTO;

public interface MemberService {

    MemberDetailResponseDto getMemberDetail(Long memberId);

    RegisterDTO.SignupSuccessDTO saveMember(Long memberId, RegisterDTO.SignUpDTO signUpDTO);

    RegisterDTO.SignupSuccessDTO saveMemberProfile(Long memberId, RegisterDTO.SignUpProfileDTO signUpProfileDTO);

    String sendVerificationCode(RegisterDTO.EmailVerificationRequestDTO requestDTO);

    String verifyEmailCode(Long memberId, RegisterDTO.EmailVerificationCodeDTO requestDTO);

    String nickDuplicate(Long memberId, String nickName);

    MyProfileDto getMyProfile(Long memberId);
}
