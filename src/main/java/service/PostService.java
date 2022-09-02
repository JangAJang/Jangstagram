package service;

import dto.PostRequestDto;
import dto.PostResponseDto;
import entity.Post;
import entity.User;
import excpetion.PostNotFoundException;
import excpetion.UserNotEqualsException;
import excpetion.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.PostRepository;
import repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

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
    public List<PostResponseDto> getAllMyPosts(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(UserNotFoundException::new);
        List<Post> posts = postRepository.findAllByWriter(user).orElseThrow(PostNotFoundException::new);
        List<PostResponseDto> postResponseDtos = new ArrayList<>();
        for(Post post: posts){
            postResponseDtos.add(PostResponseDto.toDto(post));
        }
        return postResponseDtos;
    }

    @Transactional
    public PostResponseDto writePost(PostRequestDto postRequestDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User writer = userRepository.findByUsername(authentication.getName()).orElseThrow(UserNotFoundException::new);
        Post post = Post.builder()
                .content(postRequestDto.getContent())
                .writer(writer)
                .build();
        postRepository.save(post);
        return PostResponseDto.toDto(post);
    }

    @Transactional(readOnly = true)
    public PostResponseDto readPost(Long postId){
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        return PostResponseDto.toDto(post);
    }

    @Transactional
    public PostResponseDto updatePost(Long postId, PostRequestDto postRequestDto){
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(UserNotFoundException::new);
        if(post.getWriter().equals(user)){
            post.setContent(postRequestDto.getContent());
            postRepository.save(post);
            return PostResponseDto.toDto(post);
        }
        else{
            throw new UserNotEqualsException();
        }
    }

    @Transactional
    public String deletePost(Long postId){
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(UserNotFoundException::new);
        if(post.getWriter().equals(user)){
            postRepository.delete(post);
            return "삭제 완료";
        }
        else{
            throw new UserNotEqualsException();
        }
    }
}
