package com.umc.meetpick.service.home.validator;

import com.umc.meetpick.common.exception.handler.GeneralHandler;
import com.umc.meetpick.common.response.status.ErrorCode;
import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;

public class MemberValidator {
    public static void MemberValidatorFromMember(MemberSecondProfile memberProfile) {
        if(memberProfile.getMember() == null || memberProfile.getMember().getMemberProfile() == null) {
            throw new GeneralHandler(ErrorCode.MEMBER_NOT_FOUND);
        }
    }
}
