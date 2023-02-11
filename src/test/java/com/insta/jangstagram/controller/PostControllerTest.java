package com.insta.jangstagram.controller;

import com.insta.jangstagram.repository.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void cleanDB(){
        postRepository.deleteAll();
    }

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
        //여기에서 하나가 등록되기 때문에, 다음이 등록되었을 떄 2개로 나온다.
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

    @Test
    @DisplayName("전체 테스트를 수행하면, 총 2개의 데이터가 존재한다. ")
    public void dataInTest() throws Exception{
        //given

        //when
        mvc.perform(post("/posts/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\" : \"제목\"," +
                                "\"content\" : \"내용\" }"))
                .andExpect(status().isOk())
                .andDo(print());
        //then
        Assertions.assertThat(postRepository.count()).isEqualTo(1L);
        //유닛단위에서는 1개가 맞지만 전체 테스트시에는 2개가 등록된다.
    }
}