package com.umc.meetpick.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {

    private Long senderID;
    private Long receiverID;
    private String reviewContent; //리뷰내용
}
