package dto;

import javax.validation.constraints.NotNull;

public class PostRequestDto {

    @NotNull(message = "내용을 입력하세요")
    private String content;
}
