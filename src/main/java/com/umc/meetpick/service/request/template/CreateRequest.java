package com.umc.meetpick.service.request.template;

import com.umc.meetpick.dto.RequestDTO;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.enums.Gender;
import com.umc.meetpick.enums.StudentNumber;

public abstract class CreateRequest {

    public final MemberSecondProfile execute(RequestDTO.NewRequestDTO newRequest, Member member) {
        MemberSecondProfile tempMemberSecondProfile = validate(newRequest, member);
        return process(newRequest, tempMemberSecondProfile); // TODO 일종의 연쇄 책임?
    }

    protected MemberSecondProfile validate(RequestDTO.NewRequestDTO newRequest, Member member){

        return MemberSecondProfile.builder()
                .gender(newRequest.getGender() == null ? Gender.ALL : newRequest.getGender())
                .member(member)
                .studentNumber(newRequest.getStudentNumber() == null ? StudentNumber.ALL : StudentNumber.fromString(newRequest.getStudentNumber()))
                .minAge((newRequest.getMinAge() == null || newRequest.getMinAge() < 18) ? 18 : newRequest.getMinAge())
                .maxAge((newRequest.getMaxAge() == null || newRequest.getMaxAge() > 28) ? 28 : newRequest.getMaxAge())
                .mbti(newRequest.getMbti() == null ? "INFJ" : newRequest.getMbti())
                .isHobbySame(newRequest.getIsHobbySame() != null && newRequest.getIsHobbySame())
                .maxPeople(newRequest.getMaxPeople() == 0 ? 1 : newRequest.getMaxPeople())
                .currentPeople(0)
                .comment(newRequest.getComment() == null ? "밋픽 파이팅!" : newRequest.getComment())
                .mateType(newRequest.getType()) // mateType은 null 일 수 없음
                .build();
    };

    protected abstract MemberSecondProfile process(RequestDTO.NewRequestDTO newRequest, MemberSecondProfile tempMemberSecondProfile);
}
