package controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String login(){
        return "jangstagram_login";
    }

    @GetMapping("/createId")
    public String createId(){
        return "jangstagram_createid";
    }

}
