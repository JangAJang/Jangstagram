package com.insta.jangstagram.controller;

import com.insta.jangstagram.dto.PostCreateRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class PostController {

    @GetMapping("/posts")
    public String get(){
        return "Hello World";
    }

    @PostMapping ("/posts/add")
    public String post(PostCreateRequestDto postCreateRequestDto){
        log.info("params = {}", postCreateRequestDto.toString());
        return "Hello World";
    }
}
