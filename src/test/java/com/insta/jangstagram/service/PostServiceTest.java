package com.insta.jangstagram.service;

import com.insta.jangstagram.domain.Post;
import com.insta.jangstagram.dto.PostCreateRequestDto;
import com.insta.jangstagram.dto.PostEditRequestDto;
import com.insta.jangstagram.dto.PostResponseDto;
import com.insta.jangstagram.repository.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
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

    @Test
    @DisplayName("글 1페이지 조회시에 1~5가 나온다.")
    public void getListTest() throws Exception{
        //given
        List<Post> posts = IntStream.range(1, 30)
                .mapToObj( i -> Post.builder()
                        .title("title"+i)
                        .content("content"+i).build())
                .toList();
        postRepository.saveAll(posts);

        //when
        List<PostResponseDto> page0 = postService.getList(PageRequest.of(0, 5, Sort.Direction.ASC, "id"));
        List<PostResponseDto> page1 = postService.getList(PageRequest.of(1, 5, Sort.Direction.ASC, "id"));

        //then
        assertThat(page0.size()).isEqualTo(5);
        assertThat(page0.stream().map(PostResponseDto::getTitle).collect(Collectors.toList()))
                .containsExactly("title1", "title2", "title3", "title4", "title5");
        assertThat(page1.size()).isEqualTo(5);
        assertThat(page1.stream().map(PostResponseDto::getTitle).collect(Collectors.toList()))
                .containsExactly("title6", "title7", "title8", "title9", "title10");
    }

    @Test
    @DisplayName("querydsl 페이징 조회시에 최근 게시물 순으로 페이지로 결과가 나온다.")
    public void getPageTest() throws Exception{
        //given
        List<Post> posts = IntStream.range(1, 31)
                .mapToObj( i -> Post.builder()
                        .title("title"+i)
                        .content("content"+i).build())
                .toList();
        postRepository.saveAll(posts);
        //when
        Page<PostResponseDto> page0 = postService.getPage(PageRequest.of(0, 5));
        Page<PostResponseDto> page1 = postService.getPage(PageRequest.of(1, 5));

        //then
        assertThat(page0.getContent().size()).isEqualTo(5);
        assertThat(page0.stream().map(PostResponseDto::getTitle).collect(Collectors.toList()))
                .containsExactly("title30", "title29", "title28", "title27", "title26");
        assertThat(page1.getContent().size()).isEqualTo(5);
        assertThat(page1.stream().map(PostResponseDto::getTitle).collect(Collectors.toList()))
                .containsExactly("title25", "title24", "title23", "title22", "title21");
    }

    @Test
    @DisplayName("글 제목 수정")
    public void editTitleTest() throws Exception{
        //given
        Post post = Post.builder()
                .title("취업")
                .content("하고싶다.")
                .build();
        postRepository.save(post);

        PostEditRequestDto dto = PostEditRequestDto.builder()
                .title("진짜 취업")
                .build();
        //when
        postService.edit(post.getId(), dto);
        //then
        postRepository.findById(post.getId())
                .orElseThrow(()-> new RuntimeException("글 조회 실패 : " + post.getId()));
        assertThat(post.getTitle()).isEqualTo("진짜 취업");
        assertThat(post.getContent()).isEqualTo("하고싶다.");
    }

    @Test
    @DisplayName("글 내용 수정")
    public void editContentTest() throws Exception{
        //given
        Post post = Post.builder()
                .title("취업")
                .content("하고싶다.")
                .build();
        postRepository.save(post);

        PostEditRequestDto dto = PostEditRequestDto.builder()
                .content("너무 하고싶다.")
                .build();
        //when
        postService.edit(post.getId(), dto);
        //then
        postRepository.findById(post.getId())
                .orElseThrow(()-> new RuntimeException("글 조회 실패 : " + post.getId()));
        assertThat(post.getTitle()).isEqualTo("취업");
        assertThat(post.getContent()).isEqualTo("너무 하고싶다.");
    }
}