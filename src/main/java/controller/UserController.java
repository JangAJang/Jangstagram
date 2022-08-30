package controller;

import dto.RegisterRequestDto;
import entity.User;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import response.Response;
import service.UserService;

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


}
