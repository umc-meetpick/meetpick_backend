//package com.umc.meetpick.controller;
//
//import com.umc.meetpick.common.response.ApiResponse;
//import com.umc.meetpick.dto.ContactDTO;
//import com.umc.meetpick.service.ContactService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//@Tag(name = "연락처 관련 API", description = "사용자의 연락처 정보를 설정하는 API입니다.")
//@RequiredArgsConstructor
//@RequestMapping("/api/contact")
//@RestController
//public class ContactController {
//
//    private final ContactService contactService;
//
//    @Operation(summary = "사용자의 연락처 설정 API", description = "회원 ID와 연락처 유형 및 정보를 바탕으로 연락처를 설정합니다.")
//    @PostMapping("/set")
//    public ApiResponse<ContactDTO.ContactResponseDTO> setContact(
//            @RequestParam Long memberId,
//            @RequestBody ContactDTO.ContactRequestDTO contactRequestDTO) {
//        contactRequestDTO.setMemberId(memberId); // memberId를 requestDTO에 세팅
//        return contactService.setContact(contactRequestDTO);  // 서비스 호출
//    }
//}
