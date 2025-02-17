package com.umc.meetpick.service.request.strategy;

import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.mapping.MemberSecondProfileLikes;
import com.umc.meetpick.enums.MateType;
import com.umc.meetpick.repository.member.MemberLikesRepository;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;

import static com.umc.meetpick.service.request.factory.LikeDtoFactory.getExerciseLikeResponseDto;
import static com.umc.meetpick.service.request.factory.LikeDtoFactory.getFoodLikeResponseDto;

@RequiredArgsConstructor
public class ExerciseLikeQueryStrategy implements LikeQueryStrategy{

    private final MemberLikesRepository memberLikesRepository;

    @Override
    public List<Object> process(Member member) {
        List<MemberSecondProfileLikes> memberSecondProfileLikes = memberLikesRepository.findAllByMemberAndMemberSecondProfile_MateType(member, MateType.EXERCISE);

        return Collections.singletonList(getExerciseLikeResponseDto(memberSecondProfileLikes));
    }
}
