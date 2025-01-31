package com.umc.meetpick.service;

import com.umc.meetpick.enums.University;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UniversityServiceImpl implements UniversityService {

    // TODO 레디스 사용하기
    public List<Map<String, String>> getUniversityList(String keyword) {
        return University.search(keyword);
    }
}
