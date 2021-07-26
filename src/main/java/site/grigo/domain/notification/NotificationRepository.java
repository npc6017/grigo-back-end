package site.grigo.domain.notification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.grigo.domain.account.Account;
import site.grigo.domain.post.Post;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Optional<List<Notification>> findAllByAccount(Account account);

    void deleteAllByAccountAndPost(Account account, Post post);

    boolean existsByAccount(Account account);

    boolean existsByAccountAndPost(Account account, Post post);
}
