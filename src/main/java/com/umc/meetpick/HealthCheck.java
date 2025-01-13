package com.umc.meetpick;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck {
    @GetMapping("/health")
    public String health() {
        return "안녕하세요";
    }
}
