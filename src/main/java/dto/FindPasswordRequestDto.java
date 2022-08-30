package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindPasswordRequestDto {

    @NotNull(message = "아이디를 입력하세요")
    private String username;

    @NotNull(message = "이름을 입력하세요")
    private String firstName;

    @NotNull(message = "성을 입력하세요")
    private String lastName;

    @NotNull(message = "전화번호를 입력하세요")
    private String phone;
}
