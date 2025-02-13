package com.umc.meetpick.service.home.strategy;

import com.umc.meetpick.common.exception.handler.GeneralHandler;
import com.umc.meetpick.common.response.status.ErrorCode;
import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.enums.MateType;
import com.umc.meetpick.repository.member.MemberSecondProfileRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AllStrategy implements MemberQueryStrategy {

    private final MemberSecondProfileRepository memberSecondProfileRepository;

    @Override
    public MemberSecondProfile findRandomMember(MateType mateType) {

        return memberSecondProfileRepository.findFirstBy().orElseThrow(()-> new GeneralHandler(ErrorCode.MEMBER_NOT_FOUND));
    }
}
