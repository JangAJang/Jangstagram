package com.insta.jangstagram.service;

import com.insta.jangstagram.domain.Post;
import com.insta.jangstagram.dto.PostCreateRequestDto;
import com.insta.jangstagram.dto.PostResponseDto;
import com.insta.jangstagram.repository.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;
    
    @Test
    @DisplayName("글 생성")
    public void writeTest() throws Exception{
        //given
        PostCreateRequestDto postCreateRequestDto = PostCreateRequestDto.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        //when
        postService.write(postCreateRequestDto);
        //then
        assertThat(postRepository.count()).isEqualTo(1L);
        Post post = postRepository.findAll().get(0);
        assertThat(post.getTitle()).isEqualTo("제목입니다.");
        assertThat(post.getContent()).isEqualTo("내용입니다.");
    }

    @Test
    @DisplayName("글 한개 조회")
    public void getOneTest() throws Exception{
        //given
        Post post = Post.builder()
                .title("myPage1")
                .content("this is my page no1").build();
        postRepository.save(post);

        //when
        PostResponseDto readOne = postService.getOne(post.getId());

        //then
        assertThat(readOne).isNotNull();
        assertThat(readOne.getTitle()).isEqualTo(post.getTitle());
        assertThat(readOne.getContent()).isEqualTo(post.getContent());
    }

    @Test
    @DisplayName("제목이 10글자 이상이면 10글자까지만 나온다. ")
    public void getOneTest_Title_Length() throws Exception{
        //given
        Post post = Post.builder()
                .title("12345678901234567890")
                .content("this is my page no1").build();
        postRepository.save(post);

        //when
        PostResponseDto readOne = postService.getOne(post.getId());

        //then
        assertThat(readOne).isNotNull();
        assertThat(readOne.getTitle()).isEqualTo("1234567890");
        assertThat(readOne.getContent()).isEqualTo(post.getContent());
    }

}