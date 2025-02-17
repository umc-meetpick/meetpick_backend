package com.umc.meetpick.service.request.strategy;

import com.umc.meetpick.entity.Member;

import java.util.List;

public interface LikeQueryStrategy {
    List<Object> process(Member member);
}
