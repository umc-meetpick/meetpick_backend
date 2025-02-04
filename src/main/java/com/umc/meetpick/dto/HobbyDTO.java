package com.umc.meetpick.dto;

import lombok.*;

import java.util.Set;

public class HobbyDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class HobbyRequestDTO {
        private Long memberId; // 요청한 사용자의 ID
        private Set<Integer> hobbyIds; // 선택한 취미의 id 목록
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class HobbyResponseDTO {
        private Long memberId; // 회원 ID
        private Set<String> selectedHobbies; // 선택된 취미의 이름 목록
    }
}
