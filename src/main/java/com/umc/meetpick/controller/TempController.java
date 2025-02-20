package com.umc.meetpick.controller;

import com.umc.meetpick.common.annotation.AuthUser;
import com.umc.meetpick.common.exception.handler.GeneralHandler;
import com.umc.meetpick.common.jwt.JwtUtil;
import com.umc.meetpick.common.response.ApiResponse;
import com.umc.meetpick.common.response.status.ErrorCode;
import com.umc.meetpick.dto.MemberResponseDTO;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.MemberProfiles.MemberProfile;
import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.entity.mapping.MemberSecondProfileMapping;
import com.umc.meetpick.enums.MateType;
import com.umc.meetpick.repository.member.MemberMappingRepository;
import com.umc.meetpick.repository.member.MemberProfileRepository;
import com.umc.meetpick.repository.member.MemberRepository;
import com.umc.meetpick.repository.member.MemberSecondProfileRepository;
import com.univcert.api.UnivCert;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.umc.meetpick.common.util.MemberSecondProfileUtil.findByMateType;
import static com.umc.meetpick.service.home.factory.HomeDtoFactory.MemberProfileToMemberProfileResponseDTO;

@Tag(name = "테스트 용", description = "테스트용 API")  // [변경 1]
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test") // URL 경로 설정
public class TempController {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final MemberMappingRepository memberMappingRepository;
    private final MemberSecondProfileRepository memberSecondProfileRepository;
    private final MemberProfileRepository memberProfileRepository;

    @Operation(summary = "만들어진 멤버 목록 조회", description = "만들어진 멤버 목록 조회") // [변경 2]
    @GetMapping("/member")
    public List<MemberResponseDTO> getMembers()
    {
        List<Member> members = memberRepository.findAll();

        // List<Member>를 순회하여 MemberResponseDTO로 변환
        return members.stream()
                .map(member -> MemberProfileToMemberProfileResponseDTO(member.getMemberSecondProfiles().get(0)))
                .collect(Collectors.toList());
    }

    @Operation(summary = "로그인 한 유저 정보 반환", description = "유저 정보 반환") // [변경 2]
    @GetMapping("/get-member")
    public MemberResponseDTO getMember(@AuthUser Long memberId)
    {
        Member member = memberRepository.findMemberById(memberId);

        return MemberProfileToMemberProfileResponseDTO(member.getMemberSecondProfiles().get(0));
    }

    @Operation(summary = "임시 토큰 반환", description = "임의의 유저 정보로 임시 토큰을 발급합니다") // [변경 2]
    @GetMapping("/token")
    public ApiResponse<String> generateToken()
    {
        Member member = memberRepository.findFirstByName("윤빈").orElse(null);

        return ApiResponse.onSuccess(jwtUtil.generateToken(member.getId()));
    }

    @Operation(summary = "이메일 인증 초기화") // [변경 2]
    @PostMapping("/reset")
    public Map<String, Object> resetMember() throws IOException {
        return UnivCert.clear("c5efad4c-356f-4989-949f-cbb056439ba6");
    }

    @Operation(summary = "특정 사용자한테 매칭 요청 만들기ㅣ") // [변경 2]
    @PostMapping("/get-matching/{nickName}")
    @Transactional
    public String setMatching(@PathVariable("nickName") String nickName, MateType mateType)  {

        Member member = memberRepository.findMemberByMemberProfile_Nickname(nickName).orElseThrow(()-> new GeneralHandler(ErrorCode.PROFILE_NOT_FOUND));

        List<MemberSecondProfile> memberSecondProfiles = memberSecondProfileRepository.findAll();

        List<MemberSecondProfile> memberSecondProfileList = memberSecondProfiles.stream()
                .filter(profile -> profile.getMateType() == mateType) // mateType 필터링
                .filter(profile -> profile.getMember() != member)
                .toList(); // 리스트 변환

        MemberSecondProfile memerSecondProfile = findByMateType(member.getMemberSecondProfiles(), mateType).orElseThrow(()-> new GeneralHandler(ErrorCode.PROFILE2_NOT_FOUND));

        memberMappingRepository.deleteAllByMemberSecondProfile(memerSecondProfile);
        memberMappingRepository.flush();

        for(int i = 0; i < 10; i++){
            memberMappingRepository.save(MemberSecondProfileMapping.builder()
                    .member(memberSecondProfileList.get(i).getMember())
                    .memberSecondProfile(memerSecondProfile)
                    .status(false)
                    .isAccepted(false)
                    .build());
        }
        return "성공";
    }

}


