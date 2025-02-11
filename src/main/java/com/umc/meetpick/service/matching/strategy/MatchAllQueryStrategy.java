package com.umc.meetpick.service.matching.strategy;

import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.mapping.MemberSecondProfileMapping;
import com.umc.meetpick.enums.MateType;
import com.umc.meetpick.repository.member.MemberMappingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
@Slf4j
public class MatchAllQueryStrategy implements MatchQueryStrategy{

    private final MemberMappingRepository memberMappingRepository;

    @Override
    public Page<MemberSecondProfileMapping> getMemberProfiles(Member member, MateType mateType, Pageable pageable, Boolean isAccepted) {

        return memberMappingRepository.findAllByMemberSecondProfile_MemberAndIsAccepted(member, pageable, isAccepted);
    }

}
