package com.umc.meetpick.service;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AuthenticationPrincipal(expression = "memberId") // JWT에서 추출한 memberId를 받음
public @interface CurrentUser {
}
// 코드 가독성 면에서 @AuthenticationPrincipal보다 @CurrentUser가 좋음
// 유지보수할 때 UserDetails 변경 시 모든 컨트롤러 수정 필요하지만 한 곳만 수정하면 됨
// 보안 정책 변경 시 컨트롤러 전체를 수정해야 하지만 @CurrentUser 내부만 변경하면 됨
// Expression 설정 필요할 때 "username" 등 Expression을 매번 작성해야 하지만 내부적으로 처리하여 자동 적용