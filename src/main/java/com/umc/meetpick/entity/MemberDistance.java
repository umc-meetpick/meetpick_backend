package com.umc.meetpick.entity;

import com.umc.meetpick.entity.matchingdata.food.MemberData;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberDistance {
    private MemberData memberData;
    private double distance;
}
