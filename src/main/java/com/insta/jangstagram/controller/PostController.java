package com.insta.jangstagram.controller;

import com.insta.jangstagram.dto.PostCreateRequestDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class PostController {

    @GetMapping("/posts")
    public String get(){
        return "Hello World";
    }

    @PostMapping ("/posts/add")
    public Map<String, String> post(@RequestBody @Valid PostCreateRequestDto postCreateRequestDto){
        return Map.of();
    }
}
