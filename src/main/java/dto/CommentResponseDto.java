package dto;

import entity.Comment;
import entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDto {

    private String content;

    private String username;

    public static CommentResponseDto toDto(Comment comment){
        return new CommentResponseDto(comment.getContent(), comment.getWriter().getUsername());
    }
}
