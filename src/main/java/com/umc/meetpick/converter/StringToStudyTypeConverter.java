package com.umc.meetpick.converter;

import com.umc.meetpick.enums.StudyType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToStudyTypeConverter implements Converter<String, StudyType> {
    @Override
    public StudyType convert(String source) {
        return StudyType.fromString(source);
    }
}