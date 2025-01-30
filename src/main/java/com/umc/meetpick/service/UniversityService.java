package com.umc.meetpick.service;

import com.umc.meetpick.enums.University;

import java.util.List;

public interface UniversityService {
    public List<University> getUniversityList(String keyword);
}
