package com.umc.meetpick.entity.matchingdata;

import com.umc.meetpick.entity.matchingdata.food.MemberDataFood;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberDistance<T> {
    private T memberData;
    private double distance;
}