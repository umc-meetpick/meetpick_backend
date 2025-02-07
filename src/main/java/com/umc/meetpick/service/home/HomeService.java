package com.umc.meetpick.service.home;

import com.umc.meetpick.dto.MemberResponseDTO;
import com.umc.meetpick.enums.MateType;

import java.util.List;
import java.util.Map;

public interface HomeService {

    List<Map<String, String>> getUniversityList(String keyword);

    MemberResponseDTO getRandomMember(String mateType);
}
