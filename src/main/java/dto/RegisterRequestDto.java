package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDto {

    @NotNull(message = "please Insert your last name")
    private String lastName;

    @NotNull(message = "please Insert your first name")
    private String firstName;

    @NotNull(message = "please Insert your id")
    private String username;

    @NotNull(message = "please Insert your nickname")
    private String nickName;

    @NotNull(message = "please Insert your password")
    private String password;

    @NotNull(message = "please Insert your password")
    private String passwordCheck;

    @NotNull(message = "please Insert your phone number")
    private String phone;

    @NotNull(message = "please Insert your e-mail")
    private String email;

}
