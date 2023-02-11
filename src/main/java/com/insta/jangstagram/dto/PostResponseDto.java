package com.insta.jangstagram.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostResponseDto {

    private final Long id;
    private final String title;
    private final String content;

    @Builder
    public PostResponseDto(Long id, String title, String content) {
        this.id = id;
        this.title = title.substring(0, Math.min(10, title.length()));
        this.content = content;
    }
}
