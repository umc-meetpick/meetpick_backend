package com.umc.meetpick.service.matching.factory;

import com.umc.meetpick.dto.AlarmDto;
import com.umc.meetpick.dto.MatchPageDto;
import com.umc.meetpick.dto.MatchRequestDto;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.MemberProfiles.MemberProfile;
import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.entity.mapping.MemberSecondProfileMapping;
import org.springframework.data.domain.Page;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static com.umc.meetpick.common.util.DateTimeUtil.getTime;

public class MatchingDtoFactory {

    // MemberSecondProfile 객체를 AlarmResponseDto로 변환하는 메서드
    public static AlarmDto.AlarmResponseDto memberSecondProfileToAlarmDto(MemberSecondProfileMapping mapping) {

        MemberSecondProfile memberSecondProfile = mapping.getMemberSecondProfile();

        return AlarmDto.AlarmResponseDto.builder()
                .mateType(memberSecondProfile.getMateType().getKoreanName())
                .content("새로운 알림을 확인해보세요!")
                .createdAt(getTime(mapping.getCreatedAt()))
                .memberSecondProfileId(memberSecondProfile.getId())
                .mappingId(mapping.getId())
                .build();
    }

    // MemberSecondProfileMapping 리스트를 AlarmResponseDto 리스트로 변환하는 메서드
    public static AlarmDto.AlarmPageResponseDto memberSecondProfileToAlarmDtoList(Page<MemberSecondProfileMapping> mappingList) {

        List<AlarmDto.AlarmResponseDto> alarmResponseDtoList = mappingList.stream()
                .map(MatchingDtoFactory::memberSecondProfileToAlarmDto)
                .toList();

        return   AlarmDto.AlarmPageResponseDto.builder()
                .alarms(alarmResponseDtoList)
                .currentPage(mappingList.getNumber())
                .hasNextPage(mappingList.hasNext())
                .build();
    }


    // TODo 이름 수정하기
    public static MatchRequestDto memberSecondProfileToMatchRequestDto(MemberSecondProfileMapping mapping) {

        //TODO 고치기
        Member member = mapping.getMember();
        MemberProfile memberProfile = member.getMemberProfile();
        MemberSecondProfile memberSecondProfile = mapping.getMemberSecondProfile();

        // 날짜 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        String formattedDate = memberSecondProfile.getModifiedAt().format(formatter);

        return MatchRequestDto.builder()
                .mappingId(mapping.getId())
                .memberSecondProfileId(memberSecondProfile.getId())
                .studentNumber(memberProfile.getStudentNumber() + "학번")
                .major(memberProfile.getSubMajor().getMajor().getName())
                .age(member.getAge())
                .mateType(memberSecondProfile.getMateType().getKoreanName())
                .createdAt(formattedDate)
                .build();
    }


    public static MatchPageDto memberSecondProfileToMatchPageDto(Page<MemberSecondProfileMapping> mappingList) {

        List<MatchRequestDto> matchRequestDtoList = mappingList.stream()
                .map(MatchingDtoFactory::memberSecondProfileToMatchRequestDto)
                .toList();

        return   MatchPageDto.builder()
                .matchRequestDtoList(matchRequestDtoList)
                .currentPage(mappingList.getNumber())
                .hasNextPage(mappingList.hasNext())
                .build();
    }
}
