package com.umc.meetpick.service.request.factory;

import com.umc.meetpick.enums.MateType;
import com.umc.meetpick.service.request.template.CreateExerciseRequest;
import com.umc.meetpick.service.request.template.CreateFoodRequest;
import com.umc.meetpick.service.request.template.CreateRequest;
import com.umc.meetpick.service.request.template.CreateStudyRequest;
import org.springframework.stereotype.Component;

public class CreateRequestFactory {

    public CreateRequest getCreateRequest(MateType mateType) {

        return switch (mateType) {
            case MEAL ->  new CreateFoodRequest();
            case STUDY ->  new CreateStudyRequest();
            case EXERCISE ->  new CreateExerciseRequest();
            case ALL -> null;
        };
    }
}
