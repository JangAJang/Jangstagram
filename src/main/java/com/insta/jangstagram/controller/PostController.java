package com.insta.jangstagram.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    @GetMapping("/posts")
    public String get(){
        return "Hello World";
    }

    @PostMapping ("/posts/add")
    public String post(){
        return "Hello World";
    }
}
