package site.grigo.domain;

import java.util.Optional;

public interface TokenRepository {

    void addAccessToken(Long Id, String AccessToken);
    Optional<String> findAccessTokenById(Long id);
    void deleteCache(Long id);
}
