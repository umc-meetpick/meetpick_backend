package com.umc.meetpick.service;

import com.umc.meetpick.enums.University;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UniversityServiceImpl implements UniversityService {

    public List<University> getUniversityList(String keyword) {
        return University.search(keyword);
    }
}
