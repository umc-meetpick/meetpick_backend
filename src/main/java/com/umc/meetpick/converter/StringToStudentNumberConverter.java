package com.umc.meetpick.converter;

import com.umc.meetpick.enums.StudentNumber;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToStudentNumberConverter implements Converter<String, StudentNumber> {
    @Override
    public StudentNumber convert(String source) {
        return StudentNumber.fromString(source);
    }
}