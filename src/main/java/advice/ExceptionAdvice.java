package advice;

import excpetion.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import response.Response;

@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(IllegalArgumentException.class) // 지정한 예외가 발생하면 해당 메소드 실행
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 각 예외마다 상태 코드 지정
    public Response illegalArgumentExceptionAdvice(IllegalArgumentException e) {
        return Response.failure(500, e.getMessage().toString());
    }

    //
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response methodArgumentNotValidException(MethodArgumentNotValidException e) {
        return Response.failure(400, e.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response userNotFoundException() {
        return Response.failure(404, "유저를 찾을 수 없습니다 ");
    }

    @ExceptionHandler(PasswordNotSameException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response passwordNotSameException() {
        return Response.failure(404, "유저를 찾을 수 없습니다 ");
    }

    @ExceptionHandler(AlreadyRegisteredException.class)
    @ResponseStatus(HttpStatus.ALREADY_REPORTED)
    public Response alreadyRegisteredException(){
        return Response.failure(404, "이미 회원가입한 회원입니다. ");
    }

    @ExceptionHandler(UserNotFoundPasswordException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response userNotFoundPasswordException(){
        return Response.failure(404, "사용자의 비밀번호를 확인할 수 없습니다. ");
    }
    @ExceptionHandler(UserPasswordNotEqualException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response userPasswordNotEqualException(){
        return Response.failure(404, "비밀번호가 일치하지 않습니다. ");
    }

    @ExceptionHandler(UserNotPermittedException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public Response userNotPermittedException(){
        return Response.failure(404, "사용자 권한이 존재하지 않습니다. ");
    }

    @ExceptionHandler(PostNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response postNotFoundException(){
        return Response.failure(404, "게시글을 확인할 수 없습니다.");
    }

    @ExceptionHandler(CommentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response commentNotFoundException(){
        return Response.failure(404, "댓글을 찾을 수 없습니다. ");
    }

    @ExceptionHandler(UserNotAuthorizedException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public Response userNotAuthorizedException(){
        return Response.failure(404, "권한이 없습니다. ");
    }
}