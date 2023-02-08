package com.insta.jangstagram.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest
class PostControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("/posts를 요청하면 Hello World가 출력된다. ")
    public void getTest() throws Exception{
        //given

        //when

        //then

    }

}