package com.umc.meetpick.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j; // Slf4j import 추가
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/terms")
@RequiredArgsConstructor
@Slf4j // 로그 사용을 위한 어노테이션 추가
public class TermsController {

    @GetMapping
    public String showTermsPage(@RequestParam("memberId") Long memberId, Model model) {
        model.addAttribute("memberId", memberId); // memberId를 약관 페이지로 전달
        return "terms"; // 약관 동의 페이지
    }

    @PostMapping
    public String submitTermsAgreement(@RequestParam("memberId") Long memberId) {
        log.info("약관 동의 처리 - memberId: {}", memberId); // 로그 추가
        return "redirect:/signup?memberId=" + memberId; // 회원가입 페이지로 리다이렉트
    }
}
