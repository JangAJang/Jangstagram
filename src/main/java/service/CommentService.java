package service;

import dto.CommentRequestDto;
import dto.CommentResponseDto;
import entity.Comment;
import entity.Post;
import entity.User;
import excpetion.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.CommentRepository;
import repository.PostRepository;
import repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<CommentResponseDto> getComments(Long postId){
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        List<Comment> comments = commentRepository.findAllByPost(post).orElseThrow(CommentNotFoundException::new);
        if(comments.isEmpty()) throw new CommentNotFoundException();
        List<CommentResponseDto> commentResponseDtos = new ArrayList<>();
        for(Comment comment : comments){
            commentResponseDtos.add(CommentResponseDto.toDto(comment));
        }
        return commentResponseDtos;
    }

    @Transactional
    public CommentResponseDto writeComment(Long postId, CommentRequestDto commentRequestDto){
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User writer = userRepository.findByUsername(authentication.getName()).orElseThrow(UserNotFoundException::new);
        Comment comment = Comment.builder()
                .content(commentRequestDto.getContent())
                .post(post)
                .writer(writer)
                .build();
        commentRepository.save(comment);
        return CommentResponseDto.toDto(comment);
    }

    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto commentRequestDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User writer = userRepository.findByUsername(authentication.getName()).orElseThrow(UserNotFoundException::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        if(!writer.equals(comment.getWriter())) throw new UserNotAuthorizedException();
        comment.setContent(commentRequestDto.getContent());
        commentRepository.save(comment);
        return CommentResponseDto.toDto(comment);
    }

    @Transactional
    public String deleteComment(Long commentId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User writer = userRepository.findByUsername(authentication.getName()).orElseThrow(UserNotFoundException::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        if(!writer.equals(comment.getWriter())) throw new UserNotAuthorizedException();
        commentRepository.delete(comment);
        return "삭제완료";
    }


}
