package site.grigo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/")
public class AccountController {
    /**
     * 일단 json으로 데이터를 받게 해놓았다. 변경가능.
     * @return response를  통하여 생성된 token을 헤더에 넣어서 보낸다.
     */
    @PostMapping("/login")
    public String signIn(String email, String password, HttpServletResponse response){
        return "ok";
    }
}
