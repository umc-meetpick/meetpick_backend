package com.umc.meetpick.controller;

import com.umc.meetpick.service.SignUpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/signup")
@RequiredArgsConstructor
@Slf4j
public class SignUpController {

    private final SignUpService signUpService;

    /**
     * 회원가입 페이지를 렌더링하는 메서드
     */
    @GetMapping
    public String showSignupPage(@RequestParam("memberId") Long memberId, Model model) {
        log.info("회원가입 페이지 호출 - memberId={}", memberId);
        model.addAttribute("memberId", memberId);
        return "signup"; // 회원가입 페이지
    }

    /**
     * 회원 기본 정보 및 프로필 정보를 저장한 후 이메일 인증 페이지로 리다이렉트
     */
    @PostMapping("/profile")
    public String saveMemberProfile(
            @RequestParam("memberId") Long memberId,
            @RequestParam("name") String name,
            @RequestParam("gender") String gender,
            @RequestParam("birthday") String birthday,
            @RequestParam("studentNumber") int studentNumber
    ) {
        try {
            log.info("회원 기본 정보 저장 요청: memberId={}, name={}, gender={}, birthday={}", memberId, name, gender, birthday);
            signUpService.saveMemberBasicInfo(memberId, name, gender, birthday);

            log.info("회원 프로필 정보 저장 요청: memberId={}, studentNumber={}", memberId, studentNumber);
            signUpService.saveMemberProfileInfo(memberId, studentNumber);

            log.info("회원 정보 저장 및 프로필 저장 성공");
            return "redirect:/verify?memberId=" + memberId; // 이메일 인증 페이지로 리다이렉트
        } catch (Exception e) {
            log.error("회원 정보 저장 실패", e);
            return "redirect:/error"; // 에러 페이지로 리다이렉트
        }
    }
}
