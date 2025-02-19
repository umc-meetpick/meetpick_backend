package com.umc.meetpick.service.member.validator;

import com.umc.meetpick.common.exception.handler.GeneralHandler;
import com.umc.meetpick.common.response.status.ErrorCode;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.entity.mapping.MemberSecondProfileMapping;
import com.umc.meetpick.repository.member.MemberMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberValidator {

    public static void validateContact(Member member) {

        if(member.getMemberProfile() == null){
            throw new GeneralHandler(ErrorCode.PROFILE_NOT_FOUND);
        }

        if(member.getMemberProfile().getContact() == null || member.getMemberProfile().getContactInfo() == null){
            throw new GeneralHandler(ErrorCode.CONTACT_NOT_EXIST);
        }
    }
}
