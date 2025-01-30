package com.umc.meetpick.entity;

import com.umc.meetpick.enums.PersonalityEnum;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Personality {
    private PersonalityEnum groupA;
    private PersonalityEnum groupB;
    private PersonalityEnum groupC;
    private PersonalityEnum groupD;
}

// 활기찬 조용한 상관없음
// 현실적 창의적 상관없음
// 객관적 공감만땅 상관없음
// 체계적 유동적 상관없음
