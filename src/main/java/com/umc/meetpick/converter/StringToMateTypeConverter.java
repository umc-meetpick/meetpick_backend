package com.umc.meetpick.converter;

import com.umc.meetpick.enums.MateType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToMateTypeConverter implements Converter<String, MateType> {
    @Override
    public MateType convert(String source) {
        return MateType.fromString(source);
    }
}