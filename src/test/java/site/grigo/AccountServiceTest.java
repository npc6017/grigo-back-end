package site.grigo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.grigo.domain.account.Account;
import site.grigo.domain.account.AccountRepository;
import site.grigo.domain.account.SignUpJson;
import site.grigo.service.AccountService;
import java.util.Optional;

@SpringBootTest
class AccountServiceTest {

    @Autowired
    AccountService accountService;
    @Autowired
    AccountRepository accountRepository;

    /** 테스트 계정*/
    SignUpJson testAccount = new SignUpJson(
            "solchan@grigo.me",
            "12345678",
            "solchan",
            "980427",
            60172159,
            "male",
            "01010101010");

    @Test
    @DisplayName("정상 회원 가입, 이메일로 회원 조회")
    /**
     * 계정을 저장소에 저장 -> 이메일로 조회 -> 조회한 정보와 비교
     * 서로 같으면 성공
     */
    public void save() {
;
        accountService.join(testAccount);
        Optional<Account> repositoryAccount = accountRepository.findByEmail("solchan@grigo.me");
        Assertions.assertThat(repositoryAccount.get().getEmail()).isEqualTo(testAccount.getEmail());
    }

    @Test
    @DisplayName("학번으로 회원 조회")
    /**
     * 계정을 저장소에 저장 -> 학번으로 조회 -> 조회한 정보와 비교
     * 서로 같으면 성공
     */
    public void findByStudentNumber() {
        accountService.join(testAccount);
        Optional<Account> repositoryAccount = accountRepository.findByStudentNumber(60172159);
        Assertions.assertThat(repositoryAccount.get().getStudent_id()).isEqualTo(testAccount.getStudent_id());
    }

    @Test
    @DisplayName("비밀번호 암호화")
    /**
     * 회원 등록 -> 학번으로 회원 조회 -> 조회한 정보의 비밀번호와 비교
     * 서로 다르면 성공(암호화가 정상적으로 작동하여 등록되었기 때문)
     * */
    public void hashPassword() {
        accountService.join(testAccount);
        Optional<Account> repositoryAccount = accountRepository.findByStudentNumber(60172159);
        Assertions.assertThat(repositoryAccount.get().getPassword()).isNotEqualTo(testAccount.getPassword());
    }




}
