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

        return MemberToMemberResponseDTO(selectedMember);
    }

    private MemberResponseDTO MemberToMemberResponseDTO(Member member){
        return MemberResponseDTO.builder()
                .id(member.getId())
                .nickname(member.getMemberProfile().getNickname())
                .userImage(member.getMemberProfile().getProfileImage())
                .university(member.getUniversity())
                .build();
    }
}
