package com.umc.meetpick.converter;

import com.umc.meetpick.enums.SubjectType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToSubjectTypeConverter implements Converter<String, SubjectType> {
    @Override
    public SubjectType convert(String source) {
        return SubjectType.fromString(source);
    }
}