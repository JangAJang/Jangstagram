package dto;

import entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDto {
    @NotNull
    private String content;

    @NotNull
    private String username;

    public static PostResponseDto toDto(Post post){
        PostResponseDto postResponseDto = new PostResponseDto(post.getContent(), post.getWriter().getUsername());
        return postResponseDto;
    }
}
