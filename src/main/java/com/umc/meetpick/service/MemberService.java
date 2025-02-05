package com.umc.meetpick.service;

import com.umc.meetpick.dto.MemberDetailResponseDto;
import com.umc.meetpick.dto.MemberResponseDTO;
import com.umc.meetpick.dto.RegisterDTO;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.enums.MateType;

public interface MemberService {
    MemberResponseDTO getRandomMember(MateType mateType);

    MemberDetailResponseDto getMemberDetail(Long memberId);

    RegisterDTO.SignupSuccessDTO saveMember(Long memberId, RegisterDTO.SignUpDTO signUpDTO);

    RegisterDTO.SignupSuccessDTO saveMemberProfile(Long memberId, RegisterDTO.SignUpProfileDTO signUpProfileDTO);

    String sendVerificationCode(RegisterDTO.EmailVerificationRequestDTO requestDTO);

    String verifyEmailCode(Long memberId, RegisterDTO.EmailVerificationCodeDTO requestDTO);

    String nickDuplicate(Long memberId, String nickName);
}
