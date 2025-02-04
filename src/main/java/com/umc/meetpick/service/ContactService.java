package com.umc.meetpick.service;

import com.umc.meetpick.dto.ContactDTO;
import com.umc.meetpick.common.response.ApiResponse;
import com.umc.meetpick.common.response.status.ErrorCode;
import com.umc.meetpick.entity.MemberProfiles.MemberProfile;
import com.umc.meetpick.repository.member.MemberProfileRepository;
import com.umc.meetpick.enums.ContactType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final MemberProfileRepository memberProfileRepository;

    public ApiResponse<ContactDTO.ContactResponseDTO> setContact(ContactDTO.ContactRequestDTO contactRequestDTO) {
        Long memberId = contactRequestDTO.getMemberId();
        ContactType contactType = contactRequestDTO.getContactType();
        String contactInfo = contactRequestDTO.getContactInfo();

        // 회원 프로필 찾기
        Optional<MemberProfile> optionalMemberProfile = memberProfileRepository.findByMemberId(memberId);
        if (optionalMemberProfile.isEmpty()) {
            return ApiResponse.ofFailure(ErrorCode.MEMBER_NOT_FOUND, null);
        }

        MemberProfile memberProfile = optionalMemberProfile.get(); // 프로필 값 가져오기

        // 연락처 정보 설정
        memberProfile.setContact(contactType);
        memberProfile.setContactInfo(contactInfo);

        // 변경된 프로필 저장
        memberProfileRepository.save(memberProfile);

        // 응답 DTO 생성
        ContactDTO.ContactResponseDTO responseDTO = new ContactDTO.ContactResponseDTO(
                memberProfile.getId(),
                memberProfile.getContact(),
                memberProfile.getContactInfo()
        );

        return ApiResponse.onSuccess(responseDTO);  // 성공적으로 반환
    }
}
