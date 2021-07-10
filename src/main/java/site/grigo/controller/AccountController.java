package site.grigo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import site.grigo.domain.Account;
import site.grigo.service.AccountService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class AccountController {
    private final AccountService accountService;

    @ResponseBody
    @PostMapping("/join")
    public String join(@RequestBody Account account){
        log.info("id : {} name : {} email : {} password : {}", account.getId(), account.getName(), account.getEmail(), account.getPassword());
        accountService.save(account);
        return "ok";
    }

    
}
