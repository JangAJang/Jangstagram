package com.insta.jangstagram.service;

import com.insta.jangstagram.domain.Post;
import com.insta.jangstagram.dto.PostCreateRequestDto;
import com.insta.jangstagram.dto.PostResponseDto;
import com.insta.jangstagram.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public PostResponseDto getOne(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 글입니다"));
        return PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent()).build();
    }

    public List<PostResponseDto> getList() {
        return postRepository.findAll().stream()
                .map(s -> new PostResponseDto(s.getId(), s.getTitle(), s.getContent()))
                .collect(Collectors.toList());
    }
}
