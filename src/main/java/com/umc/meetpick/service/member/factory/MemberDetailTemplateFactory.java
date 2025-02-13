package com.umc.meetpick.service.member.factory;

import com.umc.meetpick.common.exception.handler.GeneralHandler;
import com.umc.meetpick.common.response.status.ErrorCode;
import com.umc.meetpick.enums.MateType;
import com.umc.meetpick.service.member.template.GetExerciseTypeDetail;
import com.umc.meetpick.service.member.template.GetStudyTypeDetail;
import com.umc.meetpick.service.member.template.GetFoodTypeDetail;
import com.umc.meetpick.service.member.template.GetMemberDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberDetailTemplateFactory {

    public GetMemberDetail getTemplate(MateType mateType) {
        return switch (mateType) {
            case STUDY -> new GetStudyTypeDetail();
            case EXERCISE -> new GetExerciseTypeDetail();
            case MEAL -> new GetFoodTypeDetail();
            case ALL -> throw new GeneralHandler(ErrorCode._BAD_REQUEST);
        };
    }
}

