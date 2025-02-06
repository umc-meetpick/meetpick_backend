package com.umc.meetpick.service;

import com.umc.meetpick.common.exception.handler.GeneralHandler;
import com.umc.meetpick.common.response.status.ErrorCode;
import com.umc.meetpick.dto.MemberDetailResponseDto;
import com.umc.meetpick.dto.MemberResponseDTO;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.MemberProfiles.MemberProfile;
import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.enums.*;
import com.umc.meetpick.repository.member.MemberRepository;
import com.umc.meetpick.repository.member.MemberSecondProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberSecondProfileRepository memberSecondProfileRepository;
    private final MemberRepository memberRepository;

    // TODO 레디스 사용하기, 무작위 멤버 추출 방식 바꾸기
    public MemberResponseDTO getRandomMember(MateType mateType){

        long randomId = (long) (Math.random()*memberSecondProfileRepository.count());

        // TODO ID 바꾸기
        MemberSecondProfile memberProfile = memberSecondProfileRepository.findMemberSecondProfileByIdAndMateType(1L, mateType).orElseThrow(()-> new GeneralHandler(ErrorCode._BAD_REQUEST));

        return MemberProfileToMemberProfileResponseDTO(memberProfile);
    }

    @Override
    public MemberDetailResponseDto getMemberDetail(Long memberId) {
        Member member = memberRepository.findMemberById(memberId);

        // TODO mate 종류마다 달리하기 + 시간 정보
        return MemberDetailResponseDto.builder()
                .userId(memberId)
                .age(member.getAge())
                .studentNumber(member.getMemberProfile().getStudentNumber())
                .gender(member.getGender().getKoreanName())
                .mbti(member.getMemberProfile().getMBTI())
                .major(member.getMemberProfile().getMajor().getName())
                .hobbies(member.getMemberProfile().getHobbies().stream().map(Hobby::getKoreanName).collect(Collectors.toSet()))
                .foodTypes(member.getMemberSecondProfile().getFoodTypes().stream().map(FoodType::getKoreanName).collect(Collectors.toSet()))
                .comment(member.getMemberSecondProfile().getComment())
                .build();
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
