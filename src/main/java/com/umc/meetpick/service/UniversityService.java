package com.umc.meetpick.service;

import com.umc.meetpick.enums.University;

import java.util.List;
import java.util.Map;

public interface UniversityService {
    List<Map<String, String>> getUniversityList(String keyword);
}
