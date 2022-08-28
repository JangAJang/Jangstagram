package service;

import dto.RegisterRequestDto;
import dto.UserDto;
import entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.UserRepository;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserDto register(RegisterRequestDto registerRequestDto){
        if(registerRequestDto.getPasswordCheck().equals(registerRequestDto.getPassword())){
            User user = User.builder()
                    .email(registerRequestDto.getEmail())
                    .firstName(registerRequestDto.getFirstName())
                    .lastName(registerRequestDto.getLastName())
                    .nickname(registerRequestDto.getNickName())
                    .password(registerRequestDto.getPassword())
                    .phone(registerRequestDto.getPhone())
                    .role("ROLE_USER")
                    .build();
            userRepository.save(user);
            return UserDto.toDto(user);
        }
        else{
            throw new IllegalArgumentException("Password not Same");
        }
    }

    @Transactional
    public UserDto myPage(User user){
        return UserDto.toDto(user);
    }
}
