package service;

import dto.PostResponseDto;
import entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.PostRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public List<PostResponseDto> getAllPosts(){
        List<Post> posts = postRepository.findAll();
        List<PostResponseDto> postResponseDtos = new ArrayList<>();
        for(Post post: posts){
            postResponseDtos.add(PostResponseDto.toDto(post));
        }
        return postResponseDtos;
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> getAllPostsByUsername(){
        List<Post> posts = postRepository.findAll();
        List<PostResponseDto> postResponseDtos = new ArrayList<>();
        for(Post post: posts){
            postResponseDtos.add(PostResponseDto.toDto(post));
        }
        return postResponseDtos;
    }
}
