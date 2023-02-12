package com.insta.jangstagram.controller;

import com.insta.jangstagram.dto.PostCreateRequestDto;
import com.insta.jangstagram.dto.PostResponseDto;
import com.insta.jangstagram.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping ("/posts/add")
    public Map<String, String> post(@RequestBody @Valid PostCreateRequestDto postCreateRequestDto){
        postService.write(postCreateRequestDto);
        return Map.of();
    }

    @GetMapping("/posts/{id}")
    public PostResponseDto getOne(@PathVariable("id") Long id){
        return postService.getOne(id);
    }

    @GetMapping("/posts")
    public Page<PostResponseDto> getList(@PageableDefault Pageable pageable){
        return postService.getPage(pageable);
    }
}
