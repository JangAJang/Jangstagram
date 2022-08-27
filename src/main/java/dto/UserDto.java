package dto;

import entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String nickname;

    private String lastName;

    private String firstName;

    private String username;


    public static UserDto toDto(User user){
        UserDto userDto = new UserDto();
    }
}
