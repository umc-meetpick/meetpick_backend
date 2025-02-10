package com.umc.meetpick.service.member.factory;

import com.umc.meetpick.common.exception.handler.GeneralHandler;
import com.umc.meetpick.common.response.status.ErrorCode;
import com.umc.meetpick.dto.MyProfileDto;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.MemberProfiles.MemberProfile;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MemberDtoFactory {

    public static MyProfileDto memberToProfileDto(Member member) {

        MemberProfile memberProfile = member.getMemberProfile();

        if (memberProfile == null) {
            log.info("멤버의 프로필 정보 없음");
            throw new GeneralHandler(ErrorCode.PROFILE_NOT_FOUND);
        }

        return MyProfileDto.builder()
                .id(member.getId())
                .name(memberProfile.getNickname() + "(" + member.getName() + ")")
                .build();
    }
}
