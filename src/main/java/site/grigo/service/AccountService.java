package site.grigo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;
import site.grigo.domain.Account;
import site.grigo.domain.AccountRepository;
import site.grigo.domain.TokenDto;

@Service
public class AccountService {

    private AccountRepository accountRepository;
    private JwtService jwtService;

    /**
     * refactoring : email이 존재하지 않을 때의 에러와 추가적으로 주석에 기입해놓은 에러들 추가하기.
     * 남은 것들 : JwtInterceptor (implements HandlerInterceptor) -> url로 들어왔을 때, 인가해주는 것을 만들어야함.(토큰 valid 체크)
     *          AccountService의 authAccessToken을 통하여 id valid check하거나,
     *          valid check 어떻게 할지 생각해보기.
     *          로그아웃.
     *
     */
    public String login(String email, String password) throws JsonProcessingException {
        if(validateAccount(email, password)){ // 아이디와 비밀번호가 맞았을 때,
            //token이 있는지 없는지를 확인한다
            //근데 생각해보니, token이 없거나 이상이 있거나 할 때만, login로직에 접근할 것으로 생각된다.
            //따라서, login 하는 모든 경우에는 항상 token을 발급하는 것으로 하고 진행.
            TokenDto res = jwtService.createTokenResponse(accountRepository.findByEmail(email).getId());
            return res.getAccessToken();
        }
        else{
            //error 발생. 존재하지
        }
        return "ok";
    }

    public boolean validateAccount(String email, String password){
        Account account = accountRepository.findByEmail(email);
        if(account.getPassword().equals(password))
            return true;
        else
            return false;
    }

    /**
     * return 하는 값인, accessToken의 id와
     */
    public Long authAccessToken(String accessToken) throws JsonProcessingException {
        return jwtService.getPayload(accessToken);
    }
}
