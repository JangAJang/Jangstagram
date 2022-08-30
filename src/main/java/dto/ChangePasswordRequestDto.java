package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequestDto {

    @NotNull(message = "아이디를 입력하세요")
    private String username;

    @NotNull(message = "비밀번호를 입력하세요")
    private String password;

    @NotNull(message = "비밀번호를 입력하세요")
    private String newPassword;

    @NotNull(message = "비밀번호를 입력하세요")
    private String checkPassword;
}
