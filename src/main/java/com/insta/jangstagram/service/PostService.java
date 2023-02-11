package com.insta.jangstagram.service;

import com.insta.jangstagram.domain.Post;
import com.insta.jangstagram.dto.PostCreateRequestDto;
import com.insta.jangstagram.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void write(PostCreateRequestDto postCreateRequestDto){
        Post post = Post.builder()
                        .title(postCreateRequestDto.getTitle())
                        .content(postCreateRequestDto.getContent())
                        .build();
        postRepository.save(post);
    }
}
