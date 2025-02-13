package com.umc.meetpick.service.member.template;

import com.umc.meetpick.dto.MemberDetailResponseDto;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.umc.meetpick.service.member.factory.MemberDtoFactory.memberToCommonProfileDto;

@Component
@RequiredArgsConstructor
@Slf4j
public abstract class GetMemberDetail {

    public final Map<String, Object> execute(MemberSecondProfile memberSecondProfile) {
        Map<String, Object> response = new HashMap<>();

        // 공통 정보 가져오기
        MemberDetailResponseDto.MemberCommonDetailDto memberCommonDetailDto = getCommonDetail(memberSecondProfile);
        response.put("공통", memberCommonDetailDto);

        // 타입별 상세 정보 가져오기 (추상 메서드 활용)
        Object memberSecondProfileDto = getTypeDetail(memberSecondProfile);
        response.put("타입", memberSecondProfileDto);

        return response;
    }

    protected MemberDetailResponseDto.MemberCommonDetailDto getCommonDetail(MemberSecondProfile memberSecondProfile) {
        return memberToCommonProfileDto(memberSecondProfile);
    }

    protected abstract Object getTypeDetail(MemberSecondProfile memberSecondProfile);
}

