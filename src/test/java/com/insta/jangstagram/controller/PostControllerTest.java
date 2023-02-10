package com.insta.jangstagram.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
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
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("/posts/add를 요청하면 Hello World가 출력된다. ")
    public void postTest() throws Exception{
        mvc.perform(post("/posts/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\" : \"제목입니다\"," +
                                "\"content\" : \"내용입니다\" }"))
                .andExpect(content().string("{}"))
                .andExpect(status().isOk())
                .andDo(print());
    }
    
    @Test
    @DisplayName("")        
    public void testWithExceptionTitle() throws Exception{
        mvc.perform(post("/posts/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\" : \"\"," +
                                "\"content\" : \"\" }"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400")) // json값 테스트
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validationField.title").value("제목을 입력하세요"))
                .andExpect(jsonPath("$.validationField.content").value("내용을 입력하세요"))
                .andDo(print());
    }
}