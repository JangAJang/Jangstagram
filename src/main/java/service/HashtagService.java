package service;

import dto.CommentResponseDto;
import dto.HashtagRequestDto;
import dto.HashtagResponseDto;
import dto.PostResponseDto;
import entity.Comment;
import entity.Hashtag;
import entity.Post;
import excpetion.CommentNotFoundException;
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
    public List<HashtagResponseDto> getHashtagsFromPost(Long postId){
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        List<Hashtag> hashtags = hashtagRepository.findAllByPost(post).orElseThrow(HashtagNotFoundException::new);
        List<HashtagResponseDto> hashtagResponseDtos = new ArrayList<>();
        for(Hashtag hashtag : hashtags){
            hashtagResponseDtos.add(HashtagResponseDto.toDto(hashtag));
        }
        return hashtagResponseDtos;
    }

    @Transactional(readOnly = true)
    public List<HashtagResponseDto> getHashtagsFromComment(Long commentId){
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        List<Hashtag> hashtags = hashtagRepository.findAllByComment(comment).orElseThrow(HashtagNotFoundException::new);
        List<HashtagResponseDto> hashtagResponseDtos = new ArrayList<>();
        for(Hashtag hashtag : hashtags){
            hashtagResponseDtos.add(HashtagResponseDto.toDto(hashtag));
        }
        return hashtagResponseDtos;
    }

    @Transactional
    public HashtagResponseDto addHashtagOfPost(HashtagRequestDto hashtagRequestDto, Long postId){
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        Hashtag maker = Hashtag.builder()
                .content(hashtagRequestDto.getHashtag())
                .post(post)
                .build();
        hashtagRepository.save(maker);
        return HashtagResponseDto.toDto(maker);
    }

    @Transactional
    public HashtagResponseDto addHashtagOfComment(HashtagRequestDto hashtagRequestDto, Long commentId){
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        Hashtag maker = Hashtag.builder()
                .content(hashtagRequestDto.getHashtag())
                .comment(comment)
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
        List<Post> posts = postRepository.findAllByContentContaining(content).orElseThrow(PostNotFoundException::new);
        List<PostResponseDto> postResponseDtos = new ArrayList<>();
        for(Post post: posts){
            PostResponseDto tmp = PostResponseDto.toDto(post);
            if(!postResponseDtos.contains(tmp)){
                postResponseDtos.add(tmp);
            }
        }
        return postResponseDtos;
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> searchCommentbyHashtag(String content){
        List<Comment> comments = commentRepository.findAllByContentContaining(content).orElseThrow(CommentNotFoundException::new);
        List<CommentResponseDto> commentResponseDtos = new ArrayList<>();
        for(Comment comment : comments){
            CommentResponseDto tmp = CommentResponseDto.toDto(comment);
            if(!commentResponseDtos.contains(tmp)){
                commentResponseDtos.add(tmp);
            }
        }
        return commentResponseDtos;
    }
}
