package service;

import dto.RegisterRequestDto;
import dto.UserDto;
import entity.User;
import excpetion.AlreadyRegisteredException;
import excpetion.PasswordNotSameException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.UserRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserDto register(RegisterRequestDto registerRequestDto){
        List<User> users = userRepository.findAllByUsername(registerRequestDto.getUsername());
        if(!users.isEmpty()){
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
                throw new PasswordNotSameException();
            }
        }
        else{
            throw new AlreadyRegisteredException();
        }
    }

    @Transactional
    public UserDto myPage(User user){
        return UserDto.toDto(user);
    }
}
