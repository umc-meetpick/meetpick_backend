package com.umc.meetpick.common.config;


import com.umc.meetpick.common.resolver.AuthUserArgumentResolver;
import com.umc.meetpick.converter.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    private final AuthUserArgumentResolver authUserArgumentResolver;

    @Value("${front.redirect-url}")
    private String url;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("https://meetpick.click")  // 허용할 출처
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // 허용할 HTTP 메서드
                .allowCredentials(true);
    }

    public WebMvcConfig(AuthUserArgumentResolver authUserArgumentResolver) {
        this.authUserArgumentResolver = authUserArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authUserArgumentResolver);
    }
    // Converter 등록
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToMateTypeConverter());
        registry.addConverter(new StringToExerciseTypeConverter());
        registry.addConverter(new StringToFoodTypeConverter());
        registry.addConverter(new StringToSubjectTypeConverter());
        registry.addConverter(new StringToGenderConverter());
        registry.addConverter(new StringToStudentNumberConverter());

        registry.addConverter(new StringToStudyTypeConverter()); //studyType(Subject 대신 필요할 경우 사용)

    }

}

