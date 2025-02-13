package com.umc.meetpick.common.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umc.meetpick.common.response.ApiResponse;
import com.umc.meetpick.common.response.status.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException ex) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        log.info("인증되지 않은 사용자");

        response.setStatus(401);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(objectMapper.writeValueAsString(ApiResponse.ofFailure(ErrorCode._UNAUTHORIZED, ex.getMessage())));

        // 예제 13.23
        //response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
