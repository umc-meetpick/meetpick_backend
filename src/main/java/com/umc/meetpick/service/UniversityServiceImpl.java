package com.umc.meetpick.service;

import com.umc.meetpick.entity.University;
import com.umc.meetpick.repository.UniversityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UniversityServiceImpl implements UniversityService {

    private final UniversityRepository universityRepository;

    public List<University> getUniversityList() {
        return universityRepository.findAll();
    }
}
