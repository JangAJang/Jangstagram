package dto;

import entity.Hashtag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class HashtagResponseDto {

    private String content;

    public static HashtagResponseDto toDto(Hashtag hashtag){
        return new HashtagResponseDto(hashtag.getContent());
    }
}
