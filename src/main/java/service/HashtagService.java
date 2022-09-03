package service;

import dto.HashtagResponseDto;
import dto.PostResponseDto;
import entity.Comment;
import entity.Hashtag;
import entity.Post;
import excpetion.HashtagNotFoundException;
import excpetion.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.CommentRepository;
import repository.HashtagRepository;
import repository.PostRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class HashtagService {

    private final HashtagRepository hashtagRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public List<HashtagResponseDto> getHashtags(Long postId){
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        List<Hashtag> hashtags = hashtagRepository.findAllByPost(post).orElseThrow();
        List<HashtagResponseDto> hashtagResponseDtos = new ArrayList<>();
        for(Hashtag hashtag : hashtags){
            hashtagResponseDtos.add(HashtagResponseDto.toDto(hashtag));
        }
        return hashtagResponseDtos;
    }

    @Transactional
    public HashtagResponseDto addHashtag(String hashtag, Long postId){
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        Hashtag maker = Hashtag.builder()
                .content(hashtag)
                .post(post)
                .build();
        hashtagRepository.save(maker);
        return HashtagResponseDto.toDto(maker);
    }

    @Transactional
    public String deleteHashtag(Long hashtagId){
        Hashtag hashtag = hashtagRepository.findById(hashtagId).orElseThrow(HashtagNotFoundException::new);
        hashtagRepository.delete(hashtag);
        return "success deleting hashtag";
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> searchPostByHashtag(String content){
        List<Hashtag> hashtags = hashtagRepository.findAllByContent(content).orElseThrow(HashtagNotFoundException::new);
        List<Post> posts = new ArrayList<>();
        for(Hashtag hashtag : hashtags){
            if(!posts.contains(hashtag.getPost())){
                posts.add(hashtag.getPost());
            }
        }
        if(posts.isEmpty()) throw new PostNotFoundException();

        List<PostResponseDto> postResponseDtos = new ArrayList<>();
        for(Post post : posts){
            postResponseDtos.add(PostResponseDto.toDto(post));
        }
        return postResponseDtos;
    }
}
