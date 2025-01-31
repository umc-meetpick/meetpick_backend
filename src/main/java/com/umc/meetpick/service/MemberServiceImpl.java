package com.umc.meetpick.service;

import com.umc.meetpick.common.exception.handler.GeneralHandler;
import com.umc.meetpick.common.response.status.ErrorCode;
import com.umc.meetpick.dto.MemberResponseDTO;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.MemberProfiles.MemberProfile;
import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.enums.MateType;
import com.umc.meetpick.repository.member.MemberSecondProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberSecondProfileRepository memberSecondProfileRepository;

    // TODO 레디스 사용하기, 무작위 멤버 추출 방식 바꾸기
    public MemberResponseDTO getRandomMember(MateType mateType){

        long randomId = (long) (Math.random()*memberSecondProfileRepository.count());

        // TODO ID 바꾸기
        MemberSecondProfile memberProfile = memberSecondProfileRepository.findMemberSecondProfileByIdAndMateType(1L, mateType).orElseThrow(()-> new GeneralHandler(ErrorCode._BAD_REQUEST));

        return MemberProfileToMemberProfileResponseDTO(memberProfile);
    }

    private MemberResponseDTO MemberProfileToMemberProfileResponseDTO(MemberSecondProfile memberSecondProfile) {

        Member member = memberSecondProfile.getMember();
        MemberProfile memberProfile = member.getMemberProfile(); //TODO JOIN을 더 최소하 할 수 있는 방법이 있을까?

            return MemberResponseDTO.builder()
                    .id(member.getId())
                    .studentNumber(memberProfile.getStudentNumber())
                    .major(memberProfile.getMajor().getName())
                    .nickname(memberProfile.getNickname())
                    .university(member.getUniversity().toString())
                    .userImage(memberProfile.getProfileImage())
                    .comment(memberSecondProfile.getComment())
                    .build();
    }
}
