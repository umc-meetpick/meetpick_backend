package com.umc.meetpick.service.home;

import com.umc.meetpick.common.annotation.TrackExecutionTime;
import com.umc.meetpick.dto.MemberResponseDTO;
import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.enums.MateType;
import com.umc.meetpick.enums.University;
import com.umc.meetpick.repository.member.MemberSecondProfileRepository;
import com.umc.meetpick.service.home.factory.MemberQueryStrategyFactory;
import com.umc.meetpick.service.home.strategy.MemberQueryStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.umc.meetpick.service.home.factory.HomeDtoFactory.MemberProfileToMemberProfileResponseDTO;

@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {

    private final MemberSecondProfileRepository memberSecondProfileRepository;

    @TrackExecutionTime
    @Cacheable(cacheManager = "GeneralCacheManager", key = "#p0", value = "RandomCache")
    public MemberResponseDTO getRandomMember(String mateType){

        MateType type = MateType.fromString(mateType);

        MemberQueryStrategyFactory factory = new MemberQueryStrategyFactory(memberSecondProfileRepository);
        MemberQueryStrategy strategy = factory.getStrategy(type);
        MemberSecondProfile memberProfile = strategy.findRandomMember(type);

        return MemberProfileToMemberProfileResponseDTO(memberProfile);

    }

    @TrackExecutionTime
    @Cacheable(cacheManager = "GeneralCacheManager", key = "#p0", value = "UniversityCache")
    public List<Map<String, String>> getUniversityList(String keyword) {
        return University.search(keyword);
    }
}
