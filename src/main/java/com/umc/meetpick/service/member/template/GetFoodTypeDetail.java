package com.umc.meetpick.service.member.template;

import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import lombok.extern.slf4j.Slf4j;

import static com.umc.meetpick.service.member.factory.MemberDtoFactory.memberToFoodProfileDto;

@Slf4j
public class GetFoodTypeDetail extends GetMemberDetail{

    @Override
    protected Object getTypeDetail(MemberSecondProfile memberSecondProfile) {

        return memberToFoodProfileDto(memberSecondProfile);
    }
}
