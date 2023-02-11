package com.insta.jangstagram.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class PostResponseDto {

    private final Long id;
    private final String title;
    private final String content;
}
