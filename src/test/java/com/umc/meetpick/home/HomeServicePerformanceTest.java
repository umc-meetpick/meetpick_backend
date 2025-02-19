package com.umc.meetpick.home;

import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.enums.Gender;
import com.umc.meetpick.enums.MateType;
import com.umc.meetpick.enums.MemberRole;
import com.umc.meetpick.enums.MemberStatus;
import com.umc.meetpick.enums.SocialType;
import com.umc.meetpick.repository.member.MemberRepository;
import com.umc.meetpick.repository.member.MemberSecondProfileRepository;
import com.umc.meetpick.service.home.HomeService;
import com.umc.meetpick.service.member.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Date;
import java.util.Random;
import java.util.stream.IntStream;

@SpringBootTest
public class HomeServicePerformanceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberSecondProfileRepository memberSecondProfileRepository;

    @Autowired
    private HomeService homeService;

    private final Random random = new Random();

    @BeforeEach
    public void setUp() {
        // 기존 데이터 삭제
        memberSecondProfileRepository.deleteAll();
        memberRepository.deleteAll();

        // 100개의 Member 및 MemberSecondProfile 삽입
        IntStream.range(0, 1000).forEach(i -> {
            // Member 생성
            Member member = Member.builder()
                    .name("Member" + i)
                    .gender(random.nextBoolean() ? Gender.MALE : Gender.FEMALE)
                    .birthday(new Date())
                    .socialType(SocialType.KAKAO)
                    .socialId((long) i)
                    .status(MemberStatus.ACTIVE)
                    .role(MemberRole.MEMBER)
                    .build();

            member = memberRepository.save(member);
        });
    }

    @Test
    public void testGetRandomMemberExecutionTime() {
        String mateType = "STUDY";  // 임의의 MateType 설정

        // 실행 시간 측정
        homeService.getRandomMember(mateType);
    }
}

