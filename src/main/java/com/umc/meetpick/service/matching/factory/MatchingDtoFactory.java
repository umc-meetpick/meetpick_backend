package com.umc.meetpick.service.matching.factory;

import com.umc.meetpick.dto.AlarmDto;
import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.entity.mapping.MemberSecondProfileMapping;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

import static com.umc.meetpick.common.util.DateTimeUtil.getTime;

public class MatchingDtoFactory {

    // MemberSecondProfile 객체를 AlarmResponseDto로 변환하는 메서드
    public static AlarmDto.AlarmResponseDto memberSecondProfileToAlarmDto(MemberSecondProfile memberSecondProfile) {
        return AlarmDto.AlarmResponseDto.builder()
                .mateType(memberSecondProfile.getMateType().getKoreanName())
                .content("새로운 알림을 확인해보세요!")
                .createdAt(getTime(memberSecondProfile.getCreatedAt()))
                .mappingId(memberSecondProfile.getId())
                .build();
    }

    // MemberSecondProfileMapping 리스트를 AlarmResponseDto 리스트로 변환하는 메서드
    public static AlarmDto.AlarmPageResponseDto memberSecondProfileToAlarmDtoList(Page<MemberSecondProfileMapping> mappingList) {
        List<AlarmDto.AlarmResponseDto> alarmResponseDtoList = mappingList.stream()
                .map(mapping -> memberSecondProfileToAlarmDto(mapping.getMemberSecondProfile()))
                .toList();

        return   AlarmDto.AlarmPageResponseDto.builder()
                .alarms(alarmResponseDtoList)
                .currentPage(mappingList.getNumber())
                .hasNextPage(mappingList.hasNext())
                .build();
    }
}
