package com.insta.jangstagram.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(of = {"title", "content"})
public class PostCreateRequestDto {

    
    private String title;
    private String content;
}
