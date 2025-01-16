package com.umc.meetpick.service;

import com.umc.meetpick.dto.MemberResponseDTO;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    public MemberResponseDTO getRandomMember(){

        long random = (long) (Math.random()*memberRepository.count());

        Member selectedMember = memberRepository.findMemberById(random);

        return MemberResponseDTO.builder()
                .id(selectedMember.getId())
                .nickname(selectedMember.getMemberProfile().getNickname())
                .userImage(selectedMember.getMemberProfile().getProfileImage())
                .university(selectedMember.getUniversity())
                .build();
    }
}
