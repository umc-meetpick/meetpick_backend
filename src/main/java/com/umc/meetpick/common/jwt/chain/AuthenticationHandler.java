package com.umc.meetpick.common.jwt.chain;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface AuthenticationHandler {
    void setNext(AuthenticationHandler nextHandler);
    void handle(AuthContext context, HttpServletRequest request, HttpServletResponse response) throws IOException;
}
