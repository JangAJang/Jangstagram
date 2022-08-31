package service;

import config.jwt.TokenProvider;
import dto.*;
import entity.RefreshToken;
import entity.User;
import excpetion.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.RefreshTokenRepository;
import repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TokenDto tokenDto;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenProvider tokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional(readOnly = true)
    public UserDto register(RegisterRequestDto registerRequestDto) {
        List<User> users = userRepository.findAllByUsername(registerRequestDto.getUsername());
        if (!users.isEmpty()) {
            if (registerRequestDto.getPasswordCheck().equals(registerRequestDto.getPassword())) {
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
            } else {
                throw new PasswordNotSameException();
            }
        } else {
            throw new AlreadyRegisteredException();
        }
    }

    @Transactional
    public UserDto myPage(User user) {
        return UserDto.toDto(user);
    }

    // 로그인 함수
    @Transactional
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByUsername(loginRequestDto.getNickname()).orElseThrow(() -> {
            throw new UserNotFoundException();
        });

        if (passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) { // passwordEncoder.matches(받아온 pw, 데베 pw)

            // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
            UsernamePasswordAuthenticationToken authenticationToken = loginRequestDto.toAuthentication();

            // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
            //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

            // 3. 인증 정보를 기반으로 JWT 토큰 생성
            TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

            // 4. RefreshToken 저장
            RefreshToken refreshToken = RefreshToken.builder()
                    .key(authentication.getName())
                    .value(tokenDto.getRefreshToken())
                    .build();
            refreshTokenRepository.save(refreshToken);

            LoginResponseDto tokenResponseDto = new LoginResponseDto(tokenDto.getAccessToken(), tokenDto.getRefreshToken());
            // 5. 토큰 발급
            return tokenResponseDto;
        }
        throw new UserNotFoundPasswordException();
    }

    @Transactional
    public LoginResponseDto reissue(LoginResponseDto tokenRequestDto) {
        // 1. Refresh Token 검증
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        LoginResponseDto tokenResponseDto = new LoginResponseDto(tokenDto.getAccessToken(), tokenDto.getRefreshToken());
        return tokenResponseDto;
    }

    @Transactional(readOnly = true)
    public FindUsernameResponseDto findUsername(FindUsernameRequestDto findUsernameRequestDto) {
        User user = userRepository.findByPhone(findUsernameRequestDto.getPhone()).orElseThrow(UserNotFoundException::new);
        return new FindUsernameResponseDto(user.getUsername());
    }

    @Transactional(readOnly = true)
    public String findPassword(FindPasswordRequestDto findPasswordRequestDto) {
        User user = userRepository.findByUsername(findPasswordRequestDto.getUsername()).orElseThrow(UserNotFoundException::new);
        if (user.getFirstName().equals(findPasswordRequestDto.getFirstName()) &&
                user.getLastName().equals(findPasswordRequestDto.getLastName()) &&
                user.getPhone().equals(findPasswordRequestDto.getPhone())) {
            String newPassword = bCryptPasswordEncoder.encode(user.getUsername());
            user.setPassword(newPassword);
            userRepository.save(user);
            return newPassword;
        } else {
            throw new UserNotEqualsException();
        }
    }

    @Transactional
    public String changePassword(ChangePasswordRequestDto changePasswordRequestDto) {
        User user = userRepository.findByUsername(changePasswordRequestDto.getUsername()).orElseThrow(UserNotEqualsException::new);
        if (user.getPassword().equals(changePasswordRequestDto.getPassword())) {
            if (changePasswordRequestDto.getNewPassword().equals(changePasswordRequestDto.getCheckPassword())) {
                user.setPassword(changePasswordRequestDto.getNewPassword());
                userRepository.save(user);
                return "Success Changing Password!";
            } else {
                throw new UserPasswordNotEqualException();
            }
        } else {
            throw new UserNotFoundPasswordException();
        }
    }

    @Transactional
    public List<UserDto> getUsers(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User admin = userRepository.findByUsername(authentication.getName()).orElseThrow(UserNotFoundException::new);
        if(admin.getRole().equals("ROLE_USER")){
            throw new UserNotPermittedException();
        }
        else{
            List<User> users = userRepository.findAll();
            List<UserDto> userDtos = new ArrayList<>();
            for(User user : users){
                userDtos.add(UserDto.toDto(user));
            }
            return userDtos;
        }
    }

}
