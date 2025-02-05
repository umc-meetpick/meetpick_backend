package com.umc.meetpick.common.resolver;

import com.umc.meetpick.common.annotation.AuthUser;
import com.umc.meetpick.common.jwt.JwtUtil;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        boolean hasAnnotation = parameter.hasParameterAnnotation(AuthUser.class);
        boolean isMemberType = Member.class.isAssignableFrom(parameter.getParameterType());

        log.info("supportsParameter: {}", isMemberType);

        return hasAnnotation && isMemberType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {
        String bearer = webRequest.getHeader("Authorization");
        assert bearer != null;
        String token = bearer.substring(7);
        Long memberId = Long.valueOf(jwtUtil.getMemberId(token));

        return memberRepository.findMemberById(memberId).getId();
    }
}
