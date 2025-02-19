package com.umc.meetpick.home;

import com.umc.meetpick.entity.Member;
import org.junit.jupiter.api.Test;

public class HomeServiceTest {

    @Test
    public void test() {
        Member member = Member.builder()
                .birthday(new java.util.Date(2000-12-31))
                .build();

        System.out.println(member.getAge());
    }
}
