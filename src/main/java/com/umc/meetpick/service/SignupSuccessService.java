package com.umc.meetpick.service;

import com.umc.meetpick.dto.SignupSuccessDTO;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SignupSuccessService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public SignupSuccessDTO getSignupSuccessInfo(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다. memberId=" + memberId));

        log.info("🎉 회원가입 성공 - memberId: {}, name: {}", member.getId());

        return new SignupSuccessDTO(member.getId(), "회원가입이 성공적으로 완료되었습니다!");
    }
}
