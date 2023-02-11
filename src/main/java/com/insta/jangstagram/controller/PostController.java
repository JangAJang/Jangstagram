package com.insta.jangstagram.controller;

import com.insta.jangstagram.dto.PostCreateRequestDto;
import com.insta.jangstagram.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/posts")
    public String get(){
        return "Hello World";
    }

    @PostMapping ("/posts/add")
    public Map<String, String> post(@RequestBody @Valid PostCreateRequestDto postCreateRequestDto){
        postService.write(postCreateRequestDto);
        return Map.of();
    }
}
