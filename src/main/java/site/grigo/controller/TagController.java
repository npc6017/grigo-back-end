package site.grigo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/tag")
@RestController
public class TagController {

    @ResponseBody
    @PostMapping
    public String welcome(){
        // account - tag repo에 account가 존재하는지 체크후 없다면 /tag/setting 으로 redirect
        // 존재한다면, 바로 redirect homepage
        return "ok";
    }
}
