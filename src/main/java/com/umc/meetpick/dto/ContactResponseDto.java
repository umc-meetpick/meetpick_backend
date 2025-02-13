package com.umc.meetpick.dto;

import lombok.*;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactResponseDto {
    private String contactType;
    private String contactName;
}
