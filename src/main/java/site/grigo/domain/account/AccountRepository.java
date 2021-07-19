package site.grigo.domain.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    // 회원 저장
    Account save(Account account);

    // Id로 회원 찾기
    Optional<Account> findById(Long id);

    // Email로 회원 찾기
    Optional<Account> findByEmail(String email);

    // 학번으로 회원 찾기
    Optional<Account> findByStudentId(Integer studentId);

    // Email 존재 여부 확인하기
    boolean existsByEmail(String email);

    // 학번 존재 여부 확인하기
    boolean existsByStudentId(Integer studentId);

}