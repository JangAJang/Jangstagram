package com.insta.jangstagram.controller;

import com.insta.jangstagram.dto.PostCreateRequestDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class PostController {

    @GetMapping("/posts")
    public String get(){
        return "Hello World";
    }

    @PostMapping ("/posts/add")
    public Map<String, String> post(@RequestBody @Valid PostCreateRequestDto postCreateRequestDto, BindingResult result){
        if(result.hasErrors()){ // 만약 결과에 에러가 있다면
            List<FieldError> errors = result.getFieldErrors(); // 에러를 전부 가져온다.
            FieldError firstError = errors.get(0); // 에러의 리스트에서 첫 번째를 뺀다.
            String fieldName = firstError.getField(); // 에러가 난 인스턴스명(title이 없으면 title)
            String errorMessage = firstError.getDefaultMessage(); // 에러 메시지
            Map<String, String> error = new HashMap<>();
            error.put(fieldName, errorMessage);
            return error;
        }
        return Map.of();
    }
}
