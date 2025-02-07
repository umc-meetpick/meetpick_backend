package com.umc.meetpick.controller;

import com.umc.meetpick.common.annotation.AuthUser;
import com.umc.meetpick.common.jwt.JwtUtil;
import com.umc.meetpick.common.response.ApiResponse;
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

import static com.umc.meetpick.service.home.factory.HomeDtoFactory.MemberProfileToMemberProfileResponseDTO;

@Tag(name = "테스트 용", description = "테스트용 API")  // [변경 1]
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test") // URL 경로 설정
public class TempController {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final MemberSecondProfileRepository memberSecondProfileRepository;

    @Operation(summary = "만들어진 멤버 목록 조회", description = "만들어진 멤버 목록 조회") // [변경 2]
    @GetMapping("/member")
    public List<MemberResponseDTO> getMembers()
    {
        List<Member> members = memberRepository.findAll();

        // List<Member>를 순회하여 MemberResponseDTO로 변환
        return members.stream()
                .map(member -> MemberProfileToMemberProfileResponseDTO(member.getMemberSecondProfile()))
                .collect(Collectors.toList());
    }

    @Operation(summary = "로그인 한 유저 정보 반환", description = "유저 정보 반환") // [변경 2]
    @GetMapping("/get-member")
    public MemberResponseDTO getMember(@AuthUser Long memberId)
    {
        Member member = memberRepository.findMemberById(memberId);

        return MemberProfileToMemberProfileResponseDTO(member.getMemberSecondProfile());
    }

    @Operation(summary = "임시 토큰 반환", description = "임의의 유저 정보로 임시 토큰을 발급합니다") // [변경 2]
    @GetMapping("/token")
    public ApiResponse<String> generateToken()
    {
        Member member = memberRepository.findFirstBy();

        return ApiResponse.onSuccess(jwtUtil.generateToken(member.getId()));
    }

    @Operation(summary = "이메일 인증 초기화") // [변경 2]
    @PostMapping("/reset")
    public Map<String, Object> resetMember() throws IOException {
        return UnivCert.clear("c5efad4c-356f-4989-949f-cbb056439ba6");
    }

}


