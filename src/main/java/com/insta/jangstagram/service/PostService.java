package com.insta.jangstagram.service;

import com.insta.jangstagram.domain.Post;
import com.insta.jangstagram.dto.PostCreateRequestDto;
import com.insta.jangstagram.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public void write(PostCreateRequestDto postCreateRequestDto){
        Post post = Post.builder()
                        .title(postCreateRequestDto.getTitle())
                        .content(postCreateRequestDto.getContent())
                        .build();
        postRepository.save(post);

    }

    @Transactional(readOnly = true)
    public PostCreateRequestDto getOne(Long id) {
        Post post = postRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return PostCreateRequestDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }
}
