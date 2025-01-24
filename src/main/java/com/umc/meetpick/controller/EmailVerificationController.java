package com.umc.meetpick.controller;
import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpSession;

@Controller
public class EmailVerificationController {

    @Autowired
    private RestTemplate restTemplate; // 외부 API 요청을 위한 RestTemplate 객체

    /**
     * 이메일 인증 폼을 렌더링하는 메서드
     * 세션에 저장된 이메일과 대학교 이름이 있다면 폼에 기본값으로 보여줍니다.
     */
    @GetMapping("/verify")
    public String showForm(HttpSession session, Model model) {
        // 세션에서 이메일과 대학교 이름을 가져와 모델에 추가
        model.addAttribute("email", session.getAttribute("email"));
        model.addAttribute("univName", session.getAttribute("univName"));
        return "emailVerification";
    }

    /**
     * 인증 코드를 이메일로 전송하기 위한 메서드
     * 사용자가 입력한 이메일과 대학교 이름을 세션에 저장하고, API 요청을 통해 인증 코드 전송합니다.
     */
    @PostMapping("/verify")
    public String sendVerificationCode(@RequestParam String email, @RequestParam String univName, Model model, HttpSession session) {
        System.out.println("Received university name: " + StringEscapeUtils.unescapeHtml4(univName));
        System.out.println("Received email: " + email);

        // 세션에 이메일과 대학교 이름 저장
        session.setAttribute("email", email);
        session.setAttribute("univName", StringEscapeUtils.unescapeHtml4(univName));

        // HTTP 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // API 요청 바디 생성
        JSONObject requestBody = new JSONObject();
        requestBody.put("key", "c5efad4c-356f-4989-949f-cbb056439ba6"); // API 키
        requestBody.put("email", email);
        requestBody.put("univName", StringEscapeUtils.unescapeHtml4(univName));
        requestBody.put("univ_check", true);

        // API 요청 전송
        HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers);
        ResponseEntity<String> response = restTemplate.postForEntity("https://univcert.com/api/v1/certify", request, String.class);

        // API 응답 처리
        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody());
        if (response.getStatusCode().is2xxSuccessful()) {
            model.addAttribute("isCodeSent", true); // 인증 코드 전송 성공 상태 추가
            System.out.println("Code sent successfully to the email.");
        } else {
            model.addAttribute("message", "Failed to send verification code."); // 인증 코드 전송 실패 메시지 추가
            System.out.println("Failed to send verification code");
        }

        // 모델에 이메일과 대학교 이름 다시 추가 (폼 데이터 유지)
        model.addAttribute("email", email);
        model.addAttribute("univName", StringEscapeUtils.unescapeHtml4(univName));
        return "emailVerification";
    }

    /**
     * 인증 코드를 검증하기 위한 메서드
     * 입력된 인증 코드를 API를 통해 검증하고 결과를 표시합니다.
     */
    @PostMapping("/verify/verifyCode")
    public String verifyCode(@RequestParam int verificationCode, HttpSession session, Model model) {
        // 세션에서 이메일과 대학교 이름 가져오기
        String email = (String) session.getAttribute("email");
        String univName = (String) session.getAttribute("univName");

        // HTTP 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // API 요청 바디 생성
        System.out.println("verificationCode : " + verificationCode);
        JSONObject requestBody = new JSONObject();
        requestBody.put("key", "c5efad4c-356f-4989-949f-cbb056439ba6"); // API 키
        requestBody.put("email", email);
        requestBody.put("univName", StringEscapeUtils.unescapeHtml4(univName));
        requestBody.put("code", verificationCode);

        // API 요청 전송
        HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers);
        ResponseEntity<String> response = restTemplate.postForEntity("https://univcert.com/api/v1/certifycode", request, String.class);

        // API 응답 처리
        JSONObject responseBody = new JSONObject(response.getBody());
        boolean isSuccess = responseBody.getBoolean("success");

        if (response.getStatusCode().is2xxSuccessful() && isSuccess) {
            model.addAttribute("verificationStatus", "Correct"); // 인증 성공 메시지 추가
            System.out.println("Verification status: Correct");
        } else {
            model.addAttribute("verificationStatus", "Wrong"); // 인증 실패 메시지 추가
            System.out.println("Verification status: Wrong");
        }

        // 모델에 이메일과 대학교 이름 다시 추가 (폼 데이터 유지)
        model.addAttribute("email", email);
        model.addAttribute("univName", univName);
        return "emailVerification";
    }
}

