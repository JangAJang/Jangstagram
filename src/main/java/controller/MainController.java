package controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/login")
    public String login(){
        return "lgin";
    }

    @GetMapping("/createId")
    public String createId(){
        return "createId";
    }

}
