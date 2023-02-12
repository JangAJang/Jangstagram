package com.insta.jangstagram.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.insta.jangstagram.domain.Post;
import com.insta.jangstagram.dto.PostCreateRequestDto;
import com.insta.jangstagram.repository.PostRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.http.MediaType.*;
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
        //given
        String json = makeJson(new PostCreateRequestDto("제목입니다", "내용입니다"));
        //when

        //then
        mvc.perform(post("/posts/add")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(content().string("{}"))
                .andExpect(status().isOk())
                .andDo(print());
        //여기에서 하나가 등록되기 때문에, 다음이 등록되었을 떄 2개로 나온다.
    }
    
    @Test
    @DisplayName("")        
    public void testWithExceptionTitle() throws Exception{
        //given
        String json = makeJson(PostCreateRequestDto.builder().title("").content("").build());
        //when

        //ten
        mvc.perform(post("/posts/add")
                        .contentType(APPLICATION_JSON)
                        .content(json))
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
        String json = makeJson(new PostCreateRequestDto("제목", "내용"));
        //when
        mvc.perform(post("/posts/add")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());
        Post post = postRepository.findAll().get(0);
        //then
        assertThat(postRepository.count()).isEqualTo(1L);
        assertThat(post.getTitle()).isEqualTo("제목");
        assertThat(post.getContent()).isEqualTo("내용");
        //유닛단위에서는 1개가 맞지만 전체 테스트시에는 2개가 등록된다.
    }

    @Test
    @DisplayName("글 한개 조회할 때 제목과 내용 비교")
    public void getOneTest() throws Exception{
        //given
        Post post = Post.builder()
                .title("title")
                .content("content").build();
        postRepository.save(post);
        //expected
        mvc.perform(get("/posts/{postId}", post.getId())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.content").value("content"))
                .andDo(print());
    }

    @Test
    @DisplayName("글 전체 조회할 때 제목과 내용 비교")
    public void getListTest() throws Exception{
        //given
        List<Post> posts = IntStream.range(1, 30)
                .mapToObj( i -> Post.builder()
                        .title("title"+i)
                        .content("content"+i).build())
                .toList();
        postRepository.saveAll(posts);
        //expected
        mvc.perform(get("/posts?page={page}&size={size}", 1, 5)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.is(5)))
                .andExpect(jsonPath("$.[0].title").value("title1"))
                .andExpect(jsonPath("$.[0].content").value("content1"))
                .andExpect(jsonPath("$.[1].title").value("title2"))
                .andExpect(jsonPath("$.[1].content").value("content2"))
                .andExpect(jsonPath("$.[2].title").value("title3"))
                .andExpect(jsonPath("$.[2].content").value("content3"))
                .andExpect(jsonPath("$.[3].title").value("title4"))
                .andExpect(jsonPath("$.[3].content").value("content4"))
                .andExpect(jsonPath("$.[4].title").value("title5"))
                .andExpect(jsonPath("$.[4].content").value("content5"))
                .andDo(print());
    }

    private String makeJson(Object object){
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return "";
        }
    }
}