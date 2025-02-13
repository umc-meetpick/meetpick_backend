package com.umc.meetpick.service.home.factory;

import com.umc.meetpick.dto.MemberResponseDTO;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.MemberProfiles.MemberProfile;
import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;

public class HomeDtoFactory {

    public static MemberResponseDTO MemberProfileToMemberProfileResponseDTO(MemberSecondProfile memberSecondProfile) {

        Member member = memberSecondProfile.getMember();
        MemberProfile memberProfile = member.getMemberProfile(); //TODO JOIN을 더 최소하 할 수 있는 방법이 있을까?

        return MemberResponseDTO.builder()
                .id(member.getId())
                .studentNumber(memberProfile.getStudentNumber() + "학번")
                .major(memberProfile.getSubMajor().getMajor().getName())
                .gender(member.getGender().getKoreanName())
                .nickname(memberProfile.getNickname())
                .university(member.getUniversity().getKoreanName())
                .userImage(memberProfile.getProfileImage())
                .comment(memberSecondProfile.getComment())
                .build();
    }

}
