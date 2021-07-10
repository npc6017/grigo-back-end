package site.grigo.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import site.grigo.domain.Account;
import site.grigo.service.AccountService;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

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

    /**
     * 일단 모든 계정의 role은 user로 통일하였다.
     * 후에 게시판에 어떠한 관리자나 그런 게 필요하다면, 코드를 수정해야한다. role에 대한 개념이 살짝 부족함.
     * @param account
     * @return
     */
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

    public String resolveToken(HttpServletRequest request){
        return request.getHeader("X-AUTH-TOKEN");
    }

    /**
     * expire date가 넘지 않았는지 확인하는 것.
     * @param token
     * @return
     */
    public boolean validateToken(String token){
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        return !claims.getBody().getExpiration().before(new Date());
    }

    public Authentication getAuthentication(String token){
        UserDetails userDetails = accountService.loadUserByEmail(getUserEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getUserEmail(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
}
