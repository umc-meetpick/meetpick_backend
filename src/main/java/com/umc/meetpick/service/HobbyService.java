package com.umc.meetpick.service;

import com.umc.meetpick.dto.HobbyDTO;
import com.umc.meetpick.common.response.ApiResponse;
import com.umc.meetpick.common.response.status.ErrorCode;
import com.umc.meetpick.entity.MemberProfiles.MemberProfile;
import com.umc.meetpick.repository.member.MemberProfileRepository;
import com.umc.meetpick.enums.Hobby;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HobbyService {

    private final MemberProfileRepository memberProfileRepository;

    public ApiResponse<HobbyDTO.HobbyResponseDTO> setHobbies(HobbyDTO.HobbyRequestDTO hobbyRequestDTO) {
        Long memberId = hobbyRequestDTO.getMemberId();
        Set<Integer> hobbyIds = hobbyRequestDTO.getHobbyIds();

        // 최대 5개 취미 선택 가능 여부 확인
        if (hobbyIds.size() > 5) {
            return ApiResponse.ofFailure(ErrorCode.HOBBY_SELECTION_ERROR, null);
        }

        // 회원 프로필 찾기
        Optional<MemberProfile> optionalMemberProfile = memberProfileRepository.findByMemberId(memberId);
        if (optionalMemberProfile.isEmpty()) {
            return ApiResponse.ofFailure(ErrorCode.MEMBER_NOT_FOUND, null);
        }

        MemberProfile memberProfile = optionalMemberProfile.get(); // Optional에서 값 꺼내기

        // hobbyIds로 취미 Enum 목록 추출
        Set<Hobby> selectedHobbies = hobbyIds.stream()
                .map(id -> Hobby.values()[id - 1]) // hobby ID로부터 Hobby Enum 찾기
                .filter(hobby -> hobby != null)
                .collect(Collectors.toSet());

        // MemberProfile에 취미 정보 저장
        memberProfile.setHobbies(selectedHobbies);
        memberProfileRepository.save(memberProfile);

        // 응답 DTO 생성
        HobbyDTO.HobbyResponseDTO hobbyResponseDTO = new HobbyDTO.HobbyResponseDTO(memberId, selectedHobbies.stream()
                .map(Hobby::getKoreanName)  // 취미 이름을 한글로 변환
                .collect(Collectors.toSet()));
        return ApiResponse.onSuccess(hobbyResponseDTO);
    }
}
