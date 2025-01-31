package com.umc.meetpick.domain.factory;

import com.umc.meetpick.entity.Member;
import com.umc.meetpick.enums.*;

import java.util.List;
import java.util.Random;
import java.util.Date;
import java.util.stream.Collectors;

public class MemberFactory {

    private static final Random RANDOM = new Random();

    public static Member create(
            String name,
            Gender gender,
            Date birthday,
            University university,
            SocialType socialType,
            Long socialId,
            MemberStatus status,
            MemberRole role
    ) {
        return Member.builder()
                .name(name)
                .gender(gender)
                .birthday(birthday)
                .university(university)
                .socialType(socialType)
                .socialId(socialId)
                .status(status)
                .role(role)
                .build();
    }

    public static List<Member> createRandomMultiple(int count) {
        return RANDOM.ints(count, 0, getRandomNames().length)
                .mapToObj(i -> create(
                        getRandomNames()[i],
                        getRandomGender(),
                        getRandomBirthday(),
                        getRandomUniversity(),
                        getRandomSocialType(),
                        getRandomSocialId(),
                        getRandomStatus(),
                        getRandomRole()
                ))
                .collect(Collectors.toList());
    }

    private static String[] getRandomNames() {
        return new String[]{"민준", "서연", "지후", "예은", "현우", "수빈", "도윤", "채원", "하준", "지유"};
    }

    private static Gender getRandomGender() {
        Gender[] genders = Gender.values();
        return genders[RANDOM.nextInt(genders.length)];
    }

    private static Date getRandomBirthday() {
        long minDay = Date.UTC(90, 0, 1, 0, 0, 0);  // 1990년 1월 1일
        long maxDay = Date.UTC(110, 11, 31, 23, 59, 59); // 2010년 12월 31일
        return new Date(minDay + (long) (RANDOM.nextDouble() * (maxDay - minDay)));
    }

    private static University getRandomUniversity() {
        University[] universities = University.values();
        return universities[RANDOM.nextInt(universities.length)];
    }

    private static SocialType getRandomSocialType() {
        SocialType[] socialTypes = SocialType.values();
        return socialTypes[RANDOM.nextInt(socialTypes.length)];
    }

    private static Long getRandomSocialId() {
        return RANDOM.nextLong(100000, 999999);
    }

    private static MemberStatus getRandomStatus() {
        MemberStatus[] statuses = MemberStatus.values();
        return statuses[RANDOM.nextInt(statuses.length)];
    }

    private static MemberRole getRandomRole() {
        MemberRole[] roles = MemberRole.values();
        return roles[RANDOM.nextInt(roles.length)];
    }
}

