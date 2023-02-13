package com.insta.jangstagram.exception;

public class PostNotFoundException extends RuntimeException{

    private static final String message = "존재하지 않는 글입니다";

    public PostNotFoundException() {
        super(message);
    }

    public PostNotFoundException(Throwable cause) {
        super(message, cause);
    }
}
