package com.umc.meetpick.dto;

import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageRequestDto {
    private String mateType;
    private int page;
    private int size;

    public Pageable toPageable() {
        return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
    }
}
