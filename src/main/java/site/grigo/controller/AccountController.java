package site.grigo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import site.grigo.domain.Account;
import site.grigo.jwt.JwtProvider;
import site.grigo.service.AccountService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class AccountController {
    private final AccountService accountService;
    private final JwtProvider jwtProvider;

    @ResponseBody
    @PostMapping("/join")
    public String join(@RequestBody Account account) {
        log.info("id : {} name : {} email : {} password : {}", account.getId(), account.getName(), account.getEmail(), account.getPassword());
        accountService.save(account);
        return "ok";
    }

    @ResponseBody
    @PostMapping("/login")
    public String login(@RequestBody Account account) {
        log.info("email : {}, password : {}", account.getEmail(), account.getPassword());
        //토큰을 만들기 전에, 아이디와 로그인이 맞는지부터 판별할 것.
        String token = jwtProvider.createToken(account);
        return token;
    }

    @ResponseBody
    @PostMapping("/test")
    public String test(){
        log.info("heelo?");
        return "goood";
    }
}
