package controller;

import dto.*;
import excpetion.ChangeNicknameRequestDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import response.Response;
import service.UserService;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    //회원가입
    @ApiOperation(value = "회원가입", notes = "회원가입 페이지 입니다.")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/join")
    public Response register(RegisterRequestDto registerRequestDto){
        return Response.success(userService.register(registerRequestDto));
    }

    //로그인
    @ApiOperation(value = "로그인", notes = "로그인 페이지 입니다")
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public Response login(@RequestBody @Valid LoginRequestDto loginRequestDto){
        return Response.success(userService.login(loginRequestDto));
    }

    //auth 어노테이션
    @ApiOperation(value = "사용자 전체 찾기", notes = "사용자 전체 찾기 페이지입니다. ")
    @GetMapping("/auth/admin/getAll")
    @ResponseStatus(HttpStatus.OK)
    public Response getAll(){
        return Response.success(userService.getUsers());
    }

    @ApiOperation(value = "마이페이지", notes = "마이페이지를 조회합니다. ")
    @GetMapping("/auth/myPage")
    @ResponseStatus(HttpStatus.OK)
    public Response myPage(){
        return Response.success(userService.myPage());
    }

    //find 어노테이션
    @ApiOperation(value = "아이디 찾기", notes = "아이디 찾기 페이지입니다. ")
    @GetMapping("/find/username")
    @ResponseStatus(HttpStatus.OK)
    public Response findUsername(@RequestBody @Valid FindUsernameRequestDto findUsernameRequestDto){
        return Response.success(userService.findUsername(findUsernameRequestDto));
    }

    @ApiOperation(value = "비밀번호 찾기", notes = "비밀번호 찾기 페이지입니다. ")
    @GetMapping("/find/password")
    @ResponseStatus(HttpStatus.OK)
    public Response findPassword(@RequestBody @Valid FindPasswordRequestDto findPasswordRequestDto){
        return Response.success(userService.findPassword(findPasswordRequestDto));
    }

    @ApiOperation(value = "아이디 찾기", notes = "아이디 찾기 페이지입니다. ")
    @PutMapping("/change/password")
    @ResponseStatus(HttpStatus.OK)
    public Response changePassword(@RequestBody @Valid ChangePasswordRequestDto changePasswordRequestDto){
        return Response.success(userService.changePassword(changePasswordRequestDto));
    }

    @ApiOperation(value = "닉네임 변경", notes = "사용자의 닉네임을 변경합니다. ")
    @PutMapping("/change/nickname")
    @ResponseStatus(HttpStatus.OK)
    public Response changeNickname(@RequestBody @Valid ChangeNicknameRequestDto changeNicknameRequestDto){
        return Response.success(userService.changeNickname(changeNicknameRequestDto));
    }

    @ApiOperation(value = "회원 검색", notes = "회원을 검색합니다. ")
    @GetMapping("/find/")
    @ResponseStatus(HttpStatus.OK)
    public Response findUser(@RequestParam String username){
        return Response.success(userService.findUser(username));
    }
}
