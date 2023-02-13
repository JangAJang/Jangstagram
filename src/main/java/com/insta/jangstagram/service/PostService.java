package com.insta.jangstagram.service;

import com.insta.jangstagram.domain.Post;
import com.insta.jangstagram.domain.PostEditor;
import com.insta.jangstagram.dto.PostCreateRequestDto;
import com.insta.jangstagram.dto.PostEditRequestDto;
import com.insta.jangstagram.dto.PostResponseDto;
import com.insta.jangstagram.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public void write(PostCreateRequestDto postCreateRequestDto){
        Post post = Post.builder()
                        .title(postCreateRequestDto.getTitle())
                        .content(postCreateRequestDto.getContent())
                        .build();
        postRepository.save(post);

    }

    @Transactional(readOnly = true)
    public PostResponseDto getOne(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 글입니다"));
        return PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent()).build();
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> getList(Pageable pageable) {
        return postRepository.findAll(pageable).stream()
                .map(s -> new PostResponseDto(s.getId(), s.getTitle(), s.getContent()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<PostResponseDto> getPage(Pageable pageable){
        return postRepository.getPage(pageable);
    }

    @Transactional
    public void edit(Long id, PostEditRequestDto postEdit){
        Post post = postRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 글입니다"));
        PostEditor.PostEditorBuilder postEditorBuilder = post.toEditor();
        editTitle(postEdit, post, postEditorBuilder);
        editContent(postEdit, post, postEditorBuilder);
        PostEditor postEditor = postEditorBuilder
                .build();
        post.edit(postEditor);
    }

    public void delete(Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 글입니다"));

        postRepository.delete(post);
    }

    private static void editTitle(PostEditRequestDto postEdit, Post post, PostEditor.PostEditorBuilder postEditorBuilder) {
        if(postEdit.getTitle() == null) {
            postEditorBuilder.title(post.getTitle());
            return;
        }
        postEditorBuilder.title(postEdit.getTitle());
    }

    private static void editContent(PostEditRequestDto postEdit, Post post, PostEditor.PostEditorBuilder postEditorBuilder) {
        if(postEdit.getContent() == null) {
            postEditorBuilder.content(post.getContent());
            return;
        }
        postEditorBuilder.content(postEdit.getContent());
    }
}
