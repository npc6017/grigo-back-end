package site.grigo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.grigo.domain.ResponseDTO;
import site.grigo.domain.account.*;
import site.grigo.domain.account.exception.AccountInformationIncorrectException;
import site.grigo.domain.accounttag.AccountTag;
import site.grigo.domain.accounttag.AccountTagRepository;
import site.grigo.domain.notification.Notification;
import site.grigo.domain.notification.NotificationRepository;
import site.grigo.domain.post.Post;
import site.grigo.domain.post.PostDTO;
import site.grigo.domain.post.PostRepository;
import site.grigo.domain.tag.Tag;
import site.grigo.domain.tag.TagRepository;
import site.grigo.error.exception.EntityNotFoundException;
import site.grigo.jwt.JwtProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class AccountService implements UserDetailsService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder; // Password 인코딩
    private final JwtProvider jwtProvider;
    private final AccountTagRepository accountTagRepository;
    private final PostRepository postRepository; // ++
    private final NotificationRepository notificationRepository; // ++
    private final TagRepository tagRepository; // ++

    public void join(SignUpJson signUpJson) {
        // 계정 생성
        Account account = new Account(
                signUpJson.getEmail(),
                passwordEncoder.encode(signUpJson.getPassword()),
                signUpJson.getName(),
                signUpJson.getBirth(),
                signUpJson.getStudent_id(),
                signUpJson.getSex(),
                signUpJson.getPhone());
        // 등록
        accountRepository.save(account);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> account = accountRepository.findByEmail(username);
        return account.get();
    }

    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        Optional<Account> account = accountRepository.findByEmail(email);
        return account.get();
    }

    public UserDetails save(Account account) {
        return accountRepository.save(account);
    }

    //아이디가 존재하는지 찾고, 비밀번호 맞는지 확인하기.
    public boolean checkAccount(String email, String password) {
        if (checkEmail(email) && checkPassword(email, password)) return true;
        throw new EntityNotFoundException("login input invalid");
    }

    private boolean checkEmail(String email) {
        if (accountRepository.findByEmail(email).isPresent()) return true;
        throw new AccountInformationIncorrectException("email is not found");
    }

    private boolean checkPassword(String email, String password) {
        UserDetails account = accountRepository.findByEmail(email).get();

        log.info("pass : {}, saved pass : {}", password, account.getPassword());
        if (passwordEncoder.matches((CharSequence) password, account.getPassword())) return true;
        throw new AccountInformationIncorrectException("password is incorrect");
    }

    /** Get Profile Info */
    public ProfileDTO getProfile(String header) {
        Account account = getAccountToToken(header);
        ProfileDTO profile = makeProfileDTO(account);
        return profile;
    }

    public ProfileDTO getProfileFromEmail(String email){
        return makeProfileDTO(accountRepository.findByEmail(email).get());
    }


    /** User Info Update : Phone, Birth
     * 리팩토 해야하는 것 :
     * */
    public ProfileDTO updateProfile(String header, ProfileDTO profile) {
        Account account = getAccountToToken(header);

        // 수정 및 반영
        if(profile.getPhone() != null)
            account.setPhone(profile.getPhone());
        if(profile.getBirth() != null)
            account.setBirth(profile.getBirth());
        accountRepository.save(account);

        // account의 tag 업데이트 하기.
        // 삭제하고.
        List<Tag> updateTags;
        if(profile.getDeleteTags() != null){
            updateTags = extractTags(profile.getDeleteTags());
            for (Tag delete : updateTags) {
                AccountTag byAccountAndTag = accountTagRepository.findByAccountAndTag(account, delete);
                accountTagRepository.delete(byAccountAndTag);
            }
        }

        //추가해줌.
        if(profile.getAddTags() != null) {
            log.info("{}", profile.getAddTags());
            updateTags = extractTags(profile.getAddTags());
            for (Tag add : updateTags) {
                AccountTag accountTag = new AccountTag(account, add);
                accountTagRepository.save(accountTag);
            }
        }
        // ProfileDTO 생성 및 반환
        return makeProfileDTO(account);
    }

    /** TODO PassWord Update
     * @param updatePassword
     * @param header*/
    public ResponseDTO updatePassWord(PasswordUpdateDTO updatePassword, String header) {
        Account account = getAccountToToken(header);

        boolean currentPasswordMatches = passwordEncoder.matches(updatePassword.getCurrentPassword(), account.getPassword());
        boolean passwordConfirm = updatePassword.getNewPassword().equals(updatePassword.getNewPasswordConfirm());

        if(!currentPasswordMatches)  {
            return new ResponseDTO(400, "비밀번호가 일치하지 않습니다.");
        }
        if(!passwordConfirm) {
            return new ResponseDTO(400, "새로운 비밀번호가 서로 일치하지 않습니다.");
        }

        account.setPassword(passwordEncoder.encode(updatePassword.getNewPassword()));
        accountRepository.save(account);

        return new ResponseDTO(200, "비밀번호가 성공적으로 변경되었습니다.");
    }

    /* Token으로 Account 조회 */
    public Account getAccountToToken(String header) {
        String token = jwtProvider.resolveToken(header);
        String userEmail = jwtProvider.getUserEmail(token);

        return accountRepository.findByEmail(userEmail).get();
    }
    /* account로 ProfileDTO 생성(필요 없는 데이터 제거) */
    public ProfileDTO makeProfileDTO(Account account) {
        ProfileDTO profile = new ProfileDTO();
        profile.setBirth(account.getBirth());
        profile.setEmail(account.getEmail());
        profile.setPhone(account.getPhone());
        profile.setSex(account.getSex());
        profile.setName(account.getName());
        profile.setStudent_id(account.getStudentId());
        profile.setTags(getAccountTagsFromAccountToString(account));
        return profile;
    }

    /** getAccountTagsFromEmailToString -> getAccountTagsFromAccountToString */
    public List<String> getAccountTagsFromAccountToString(Account account) {
        List<String> res = new ArrayList<>();
        Optional<List<AccountTag>> allByEmail = accountTagRepository.findAllByAccount(account);
        for(AccountTag tag : allByEmail.get())
            res.add(tag.getTag().getName());
        return res;
    }

    /** 알림 생성
     * @param post
     * */
    public void setNotification(Post post, PostDTO postDTO) {

        // Get Post
        Optional<Post> getpost = postRepository.findById(post.getId());

        // 태그를 가지고 있는 Account 가져오기
        List<AccountTag> accountTags = new ArrayList<>();
        List<Tag> tags = extractTags(postDTO.getTags());
        tags.forEach(tag -> {
            Optional<List<AccountTag>> byTagName = accountTagRepository.findByTag(tag);
            accountTags.addAll(byTagName.get());
        });

        /** 가져온 모든 Account와 Post 알림 등록
         * 한 게시글에 유저가 가지고 있는 태그가 2개 이상 등록되어 있는 경우,
         * 하나의 알림으로 처리하기 위해 중복을 검사하여 처리한다.
         */
        accountTags.forEach( accountTag -> {
            boolean isNotification = notificationRepository.existsByAccountAndPost(accountTag.getAccount(), getpost.get());
            if(!isNotification)
                notificationRepository.save(new Notification(accountTag.getAccount(),getpost.get()));
        });

        // 가져온 모든 Account의 새알림 여부 변경
        accountTags.forEach( accountTag -> {
            accountTag.getAccount().setCheckNotice(true);
            accountRepository.save(accountTag.getAccount());
        });
    }

    /**
     * 알림 읽음 처리
     * */
    @Transactional /** delete 실행 시 Transaction 필요*/
    public void deleteNotice(Account account, Long postId) {
        Optional<Post> post = postRepository.findById(postId);
        notificationRepository.deleteAllByAccountAndPost(account, post.get());

        boolean isNotification = notificationRepository.existsByAccount(account);
        /** 유저가 모든 알림을 읽은 경우 */
        if(!isNotification) {
            account.setCheckNotice(false);
            accountRepository.save(account);
        }
    }

    private List<Tag> extractTags(List<String> tagsFromDto) {
        List<Tag> tags = new ArrayList<>();
        for(String tag : tagsFromDto) {
            Optional<Tag> byName = tagRepository.findByName(tag);
            if(!byName.isPresent()) {
                Tag newTag = new Tag(tag);
                tagRepository.save(newTag);
                log.info("{}", newTag);
                tags.add(newTag);
            }
            else tags.add(byName.get());
        }
        
        return tags;
    }
}
