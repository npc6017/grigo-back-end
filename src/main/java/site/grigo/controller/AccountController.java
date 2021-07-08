package site.grigo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/")
public class AccountController {

    @PostMapping("/login")
    public String signIn(String email, String password, HttpServletResponse response){
        return "ok";
    }
}
