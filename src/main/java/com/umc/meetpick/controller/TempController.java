package com.umc.meetpick.controller;

import com.umc.meetpick.common.annotation.AuthUser;
import com.umc.meetpick.dto.MemberResponseDTO;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.repository.member.MemberRepository;
import com.umc.meetpick.repository.member.MemberSecondProfileRepository;
import com.univcert.api.UnivCert;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Tag(name = "temp", description = "테스트용 API")  // [변경 1]
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test") // URL 경로 설정
public class TempController {

    private final MemberRepository memberRepository;
    private final MemberSecondProfileRepository memberSecondProfileRepository;

    @Operation(summary = "만들어진 멤버 목록 조회", description = "만들어진 멤버 목록 조회") // [변경 2]
    @GetMapping("/member")
    public List<MemberResponseDTO> getMembers()
    {
        List<Member> members = memberRepository.findAll();

        // List<Member>를 순회하여 MemberResponseDTO로 변환

        return members.stream()
                .map(member -> MemberResponseDTO.builder()
                        .id(member.getId())
                        .studentNumber(member.getMemberProfile().getStudentNumber() + "학번")
                        .major(member.getMemberProfile().getSubMajor().getName())
                        .nickname(member.getMemberProfile().getNickname())
                        .university(member.getUniversity().toString())
                        .userImage(member.getMemberProfile().getProfileImage())
                        .comment(member.getMemberSecondProfile().getComment())
                        .build())
                .collect(Collectors.toList());
    }

    @Operation(summary = "로그인 한 유저 정보 반환", description = "유저 정보 반환") // [변경 2]
    @GetMapping("/get-member")
    public Long getMember(@AuthUser Long memberId)
    {
        return memberId;
    }

    @Operation(summary = "이메일 인증 초기화") // [변경 2]
    @PostMapping("/reset")
    public Map<String, Object> resetMember() throws IOException {
        return UnivCert.clear("c5efad4c-356f-4989-949f-cbb056439ba6");
    }

}


