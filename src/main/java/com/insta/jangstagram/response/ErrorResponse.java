package com.insta.jangstagram.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Getter
@Builder
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class ErrorResponse {

    private final String code;
    private final String message;
    private final Map<String, String> validationField = new HashMap<>();

    public void addValidation(String field, String defaultMessage) {
        this.validationField.put(field, defaultMessage);
    }
}
