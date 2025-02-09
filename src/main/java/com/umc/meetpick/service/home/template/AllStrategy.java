package com.umc.meetpick.service.home.template;

import com.umc.meetpick.common.exception.handler.GeneralHandler;
import com.umc.meetpick.common.response.status.ErrorCode;
import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.enums.MateType;
import com.umc.meetpick.repository.member.MemberSecondProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.web.WebProperties;

@RequiredArgsConstructor
public class AllStrategy implements MemberQueryStrategy {

    private final MemberSecondProfileRepository memberSecondProfileRepository;

    @Override
    public MemberSecondProfile findRandomMember(String mateType) {

        return memberSecondProfileRepository.findFirstBy().orElseThrow(()-> new GeneralHandler(ErrorCode.MEMBER_NOT_FOUND));
    }
}
