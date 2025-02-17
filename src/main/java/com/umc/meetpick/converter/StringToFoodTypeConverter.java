package com.umc.meetpick.converter;

import com.umc.meetpick.enums.FoodType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToFoodTypeConverter implements Converter<String, FoodType> {
    @Override
    public FoodType convert(String source) {
        return FoodType.fromString(source);
    }
}