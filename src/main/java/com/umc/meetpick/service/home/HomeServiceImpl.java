package com.umc.meetpick.service.home;

import com.umc.meetpick.dto.MemberResponseDTO;
import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.enums.MateType;
import com.umc.meetpick.enums.University;
import com.umc.meetpick.repository.member.MemberSecondProfileRepository;
import com.umc.meetpick.service.home.factory.MemberQueryStrategyFactory;
import com.umc.meetpick.service.home.strategy.MemberQueryStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.umc.meetpick.service.home.factory.HomeDtoFactory.MemberProfileToMemberProfileResponseDTO;

@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {

    private final MemberSecondProfileRepository memberSecondProfileRepository;

    // TODO 레디스 사용하기, 무작위 멤버 추출 방식 바꾸기
    public MemberResponseDTO getRandomMember(String mateType){

        MateType type = MateType.fromString(mateType);

        MemberQueryStrategyFactory factory = new MemberQueryStrategyFactory(memberSecondProfileRepository);
        MemberQueryStrategy strategy = factory.getStrategy(type);
        MemberSecondProfile memberProfile = strategy.findRandomMember(type);

        return MemberProfileToMemberProfileResponseDTO(memberProfile);

    }

    // TODO 레디스 사용하기
    public List<Map<String, String>> getUniversityList(String keyword) {
        return University.search(keyword);
    }
}
