package com.umc.meetpick.service;

import com.umc.meetpick.dto.TermsDTO;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TermsService {

    private final MemberRepository memberRepository;

    @Transactional
    public void agreeTerms(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));
        member.setTermsAgreed(true);
        memberRepository.save(member);
    }

    @Transactional
    public TermsDTO getTermsStatus(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        // ✅ 올바른 생성자 호출
        return new TermsDTO(member.getId(), member.isTermsAgreed());
    }
}
