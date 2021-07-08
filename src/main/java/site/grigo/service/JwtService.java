package site.grigo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import site.grigo.domain.TokenDto;
import site.grigo.domain.TokenRepository;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class JwtService {
    private final TokenRepository tokenRepository;
    private final ObjectMapper objectMapper;
    private final Long validateTime = 14 * 24 * 60 * 60 * 1000L; // 날짜 x 시간 x 분 x 초 x ms

    @Value("${encryption}")
    private String SECRET_KEY;

    public String createToken(Long id) throws JsonProcessingException {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256; // 암호 알고리즘
        Date expireTime = new Date(); // 파기되는 날짜
        expireTime.setTime(expireTime.getTime() + validateTime); // 파기되는 시간 설정
        byte[] secretKeyBytes = generateKey(); // 뭐지?

        Map<String, Object> headerMap = new HashMap<>();

        headerMap.put("typ", "JWT");
        headerMap.put("alg", "HS256");

        //암호화
        Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());

        return Jwts.builder()
                .setHeader(headerMap)
                .setSubject(objectMapper.writeValueAsString(id))
                .setExpiration(expireTime)
                .signWith(signingKey, signatureAlgorithm)
                .compact();
    }

    private byte[] generateKey(){
        byte[] key;
        key = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return key;
    }

    /**
     * @return token에 payload에 있는 id를 해석하여 return.
     */
    public Long getPayload(String token) throws JsonProcessingException {
        Claims claims = getAllClaimsFromToken(token);
        return objectMapper.readValue(claims.getSubject(), Long.class);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(generateKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public TokenDto createTokenResponse(Long userId) throws JsonProcessingException {
        String accessToken = createToken(userId);

        Optional<String> check = tokenRepository.findAccessTokenById(userId);
        if(check != null) tokenRepository.deleteCache(userId);
        tokenRepository.addAccessToken(userId, accessToken);

        return TokenDto.builder()
                .accessToken(accessToken)
                .build();
    }
}
