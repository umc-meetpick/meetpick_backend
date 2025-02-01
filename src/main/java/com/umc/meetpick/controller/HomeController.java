package com.umc.meetpick.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "홈화면 API", description = "로그인했을 때 신규회원이라면 회원가입 및 대학교 인증 후 /home으로, 기존 회원이면 /home으로 redirect ")

@RestController
@RequestMapping("/home")
public class HomeController {

    @GetMapping
    public String home() {
        return "HomePage"; //홈화면
    }
}
