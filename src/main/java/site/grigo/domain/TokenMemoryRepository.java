package site.grigo.domain;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class TokenMemoryRepository implements TokenRepository{

    private static final Map<Long, String> store = new HashMap<>();

    @Override
    public void addAccessToken(Long id, String accessToken) {
        store.put(id, accessToken);
    }

    @Override
    public Optional<String> findAccessTokenById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    /**
     * 지금 생각은 어차피 token은 한개밖에 존재하지 않는다고 생각하기 때문에 store에 있는 id 하나만 삭제하는 방식이지만,
     * 혹시 여러개가 존재할 수도 있기 때문에 removeAll같은 것이 필요할 것 같다.
     */
    public void deleteCache(Long id) {
        store.remove(id);
    }
}
