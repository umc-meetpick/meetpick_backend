package com.umc.meetpick.controller;

import com.umc.meetpick.common.response.ApiResponse;
import com.umc.meetpick.dto.MatchResponseDto;
import com.umc.meetpick.dto.MemberResponseDTO;
import com.umc.meetpick.entity.Major;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.enums.*;
import com.umc.meetpick.repository.member.MemberRepository;
import com.umc.meetpick.repository.member.MemberSecondProfileRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Tag(name = "temp", description = "테스트용 API")  // [변경 1]
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test") // URL 경로 설정
public class TempController {

    private final MemberRepository memberRepository;
    private final MemberSecondProfileRepository memberSecondProfileRepository;

    @Operation(summary = "만들어진 멤버 목록 조회", description = "만들어진 멤버 목록 조회") // [변경 2]
    @GetMapping("member")
    public List<MemberResponseDTO> getMember()
    {
        List<Member> members = memberRepository.findAll();

        // List<Member>를 순회하여 MemberResponseDTO로 변환

        return members.stream()
                .map(member -> MemberResponseDTO.builder()
                        .id(member.getId())
                        .studentNumber(member.getMemberProfile().getStudentNumber())
                        .major(member.getMemberProfile().getMajor().getName())
                        .nickname(member.getMemberProfile().getNickname())
                        .university(member.getUniversity().toString())
                        .userImage(member.getMemberProfile().getProfileImage())
                        .comment(member.getMemberSecondProfile().getComment())
                        .build())
                .collect(Collectors.toList());
    }

}


