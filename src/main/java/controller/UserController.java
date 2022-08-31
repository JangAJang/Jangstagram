package controller;

import dto.*;
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

    @ApiOperation(value = "회원가입", notes = "회원가입 페이지 입니다.")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/join")
    public Response register(RegisterRequestDto registerRequestDto){
        return Response.success(userService.register(registerRequestDto));
    }

    @ApiOperation(value = "로그인", notes = "로그인 페이지 입니다")
    @PostMapping("/auth/login")
    @ResponseStatus(HttpStatus.OK)
    public Response login(@RequestBody @Valid LoginRequestDto loginRequestDto){
        return Response.success(userService.login(loginRequestDto));
    }

    @ApiOperation(value = "아이디 찾기", notes = "아이디 찾기 페이지입니다. ")
    @GetMapping("/find/username")
    @ResponseStatus(HttpStatus.OK)
    public Response findUsername(@RequestBody @Valid FindUsernameRequestDto findUsernameRequestDto){
        return Response.success(userService.findUsername(findUsernameRequestDto));
    }

    @ApiOperation(value = "아이디 찾기", notes = "아이디 찾기 페이지입니다. ")
    @GetMapping("/find/password")
    @ResponseStatus(HttpStatus.OK)
    public Response findPassword(@RequestBody @Valid FindPasswordRequestDto findPasswordRequestDto){
        return Response.success(userService.findPassword(findPasswordRequestDto));
    }

    @ApiOperation(value = "아이디 찾기", notes = "아이디 찾기 페이지입니다. ")
    @PutMapping("/find/password")
    @ResponseStatus(HttpStatus.OK)
    public Response changePassword(@RequestBody @Valid ChangePasswordRequestDto changePasswordRequestDto){
        return Response.success(userService.changePassword(changePasswordRequestDto));
    }

    @ApiOperation(value = "사용자 전체 찾기", notes = "사용자 전체 찾기 페이지입니다. ")
    @GetMapping("/auth/admin/getAll")
    @ResponseStatus(HttpStatus.OK)
    public Response getAll(){
        return Response.success(userService.getUsers());
    }



}
