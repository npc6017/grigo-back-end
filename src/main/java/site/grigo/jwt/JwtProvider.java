package site.grigo.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import site.grigo.domain.Account;
import site.grigo.service.AccountService;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class JwtProvider {

    private String secretKey = "secretKey설정";
    private long tokenValidTime = 60 * 60 * 1000L; //1시간 설정

    private final AccountService accountService;

    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(Account account){
        Claims claims = Jwts.claims().setSubject(account.getEmail());
        claims.put("role", "user");
        return doGenerateToken(claims);
    }

    private String doGenerateToken(Claims claims){
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }
}
