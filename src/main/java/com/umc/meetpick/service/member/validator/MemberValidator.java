package com.umc.meetpick.service.member.validator;

import com.umc.meetpick.common.exception.handler.GeneralHandler;
import com.umc.meetpick.common.response.status.ErrorCode;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.mapping.MemberSecondProfileMapping;
import com.umc.meetpick.repository.member.MemberMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberValidator {

    private final MemberMappingRepository memberMappingRepository;

    public void validateContact(Member member, Long mappingId){

        if(member == null) {
            throw new GeneralHandler(ErrorCode.MEMBER_NOT_FOUND);
        }

        MemberSecondProfileMapping mapping = memberMappingRepository.findById(mappingId).orElseThrow(()->new GeneralHandler(ErrorCode._BAD_REQUEST));

        if(mapping.getMemberSecondProfile().getMember() != member || !mapping.getIsAccepted()){
            throw new GeneralHandler(ErrorCode._FORBIDDEN);
        }
    }
}
