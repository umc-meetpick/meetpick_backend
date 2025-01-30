package com.umc.meetpick.service;

import com.umc.meetpick.common.response.ApiResponse;
import com.umc.meetpick.dto.SignUpDTO;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.enums.Gender;
import com.umc.meetpick.repository.MemberRepository;
import com.umc.meetpick.common.response.status.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class SignUpService {

    private final MemberRepository memberRepository;

    @Transactional
    public ApiResponse<SignUpDTO> processSignup(Long memberId, SignUpDTO signUpDTO) {
        try {
            log.info("회원 기본 정보 저장 시작 - memberId={}, name={}, gender={}, birthday={}",
                    memberId, signUpDTO.getName(), signUpDTO.getGender(), signUpDTO.getBirthday());

            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new RuntimeException("Member not found with id: " + memberId));

            Gender newGender = signUpDTO.getGender().equals("남성") ? Gender.MALE : Gender.FEMALE;
            Date newBirthday = Date.valueOf(signUpDTO.getBirthday());

            if (newGender.equals(member.getGender()) && newBirthday.equals(member.getBirthday())) {
                log.info("⚠ 회원 정보 변경 사항 없음 - 저장 생략");
                return ApiResponse.onSuccess(signUpDTO);
            }

            // 변경 사항이 있을 경우에만 업데이트
            member.setGender(newGender);
            member.setBirthday(newBirthday);
            memberRepository.save(member);  // 업데이트 후 저장

            // 응답 객체에 memberId 추가
            signUpDTO.setMemberId(memberId);

            log.info("✅ 회원 기본 정보 업데이트 완료: {}", member);
            return ApiResponse.onSuccess(signUpDTO);

        } catch (Exception e) {
            log.error("회원 정보 저장 실패", e);
            return ApiResponse.onFailure(ErrorCode.MEMBER_SIGNUP_ERROR.getCode(),
                    ErrorCode.MEMBER_SIGNUP_ERROR.getMessage(), null);
        }
    }
}
