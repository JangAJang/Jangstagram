package com.insta.jangstagram.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class PostControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("/posts를 요청하면 Hello World가 출력된다. ")
    public void getTest() throws Exception{
        mvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World"));
    }
    @Test
    @DisplayName("/posts/add를 요청하면 Hello World가 출력된다. ")
    public void postTest() throws Exception{
        mvc.perform(post("/posts/add")
                        .param("title", "제목입니다")
                        .param("content", "내용입니다"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World"))
                .andDo(print());
    }

}