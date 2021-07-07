package site.grigo.domain.account;

import java.util.Optional;

public interface AccountRepository {

    // 회원 저장
    Account save(Account account);

    // Id로 회원 찾기
    Optional<Account> findById(Long id);

    // Email로 회원 찾기
    Optional<Account> findByEmail(String email);

    // 학번으로 회원 찾기
    Optional<Account> findByStudentNumber(Integer studentNumber);

    // Email 존재 여부 확인하기
    boolean existsByEmail(String email);

    // 학번 존재 여부 확인하기
    boolean existsByStudentNumber(Integer studentNumber);

}
