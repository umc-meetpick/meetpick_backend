package com.umc.meetpick.converter;

import com.umc.meetpick.enums.ExerciseType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToExerciseTypeConverter implements Converter<String, ExerciseType> {
    @Override
    public ExerciseType convert(String source) {
        return ExerciseType.fromString(source);
    }
}