package com.umc.meetpick.service.request.template;

import com.umc.meetpick.dto.RequestDTO;
import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.enums.ExerciseType;
import com.umc.meetpick.enums.StudyType;

public class CreateStudyRequest extends CreateRequest {

    @Override
    protected MemberSecondProfile process(RequestDTO.NewRequestDTO newRequest, MemberSecondProfile tempMemberSecondProfile) {

        tempMemberSecondProfile.setStudyType(newRequest.getStudyType() == null ? StudyType.STUDY : StudyType.fromString(newRequest.getStudyType()));
        if(newRequest.getMajorNameAndProfessorName().contains("-")){
            String[] parts = newRequest.getMajorNameAndProfessorName().split("-");
            tempMemberSecondProfile.setMajorName(parts[0]);
            tempMemberSecondProfile.setProfessorName(parts[1]);
        }
        tempMemberSecondProfile.setStudyTimes(newRequest.getStudyTimes() == null ? 1 : newRequest.getStudyTimes());
        tempMemberSecondProfile.setPlace(newRequest.getPlace() == null ? "기타" : newRequest.getPlace());

        if(newRequest.getIsOnline() != null) {
            tempMemberSecondProfile.setIsOnline(!newRequest.getIsOnline().equals("오프라인"));
        } else {
            tempMemberSecondProfile.setIsOnline(false);
        }

        return tempMemberSecondProfile;
    }
}
