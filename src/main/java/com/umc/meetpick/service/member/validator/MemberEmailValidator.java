package com.umc.meetpick.service.member.validator;

import com.umc.meetpick.common.exception.handler.GeneralHandler;
import com.umc.meetpick.common.response.status.ErrorCode;
import com.umc.meetpick.entity.Member;

public class MemberEmailValidator {

    public static void isMemberEmailVerified(Member member) {
        if(!member.isVerified()) throw new GeneralHandler(ErrorCode.EMAIL_NOT_VERIFIED);
    }
}
