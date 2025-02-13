package com.umc.meetpick.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WeekPair<K, V> {
    private K week;
    private V times;
}