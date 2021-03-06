package site.grigo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import site.grigo.domain.account.Account;
import site.grigo.domain.ResponseDTO;
import site.grigo.domain.account.SignUpJson;
import site.grigo.domain.account.*;
import site.grigo.domain.notification.Notification;
import site.grigo.domain.notification.NotificationDTO;
import site.grigo.domain.notification.NotificationRepository;
import site.grigo.error.exception.EntityNotFoundException;
import site.grigo.jwt.JwtProvider;
import site.grigo.service.AccountService;
import site.grigo.validator.SignUpValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class AccountController {
    private final AccountService accountService;
    private final JwtProvider jwtProvider;

    private final SignUpValidator signUpValidator; // 이메일 검증 객체

    private final NotificationRepository notificationRepository;

    /**
     * SignUpJson으로 파리미터가 들어오는 경우, 검증
     */
    @InitBinder("signUpJson")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signUpValidator); // 검증 객체 등록
    }

    @PostMapping("/join")
    public ResponseDTO accountJoin(@Valid @RequestBody SignUpJson signUpJson, HttpServletResponse response, Errors errors) {
        /** 검증 조건에 부합하지 않는 경우, 메세지 응답. */
        if (errors.hasErrors()) {
            response.setStatus(404);
            return new ResponseDTO(404, errors.getFieldError().getDefaultMessage());
        }
        /** Account 가입 */
        accountService.join(signUpJson);
        log.info(signUpJson.getEmail() + " " + signUpJson.getPassword());
        response.setStatus(200);
        return new ResponseDTO(200, "Success"); // 리턴 내용은 언제든 수정 가능
    }

    /**
     * MethodArgumentNotValidException 핸들러(객체 변수 어노테이션(@NotBlank 등))
     * 해당 예외가 잡히면, Bad Request 응답코드 400과 함께 에러 메시지를 응답해준다.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseDTO processValidationError(MethodArgumentNotValidException exception, HttpServletResponse response) {
        String errorMessage = exception.getFieldError().getDefaultMessage();
        if (exception.hasErrors()) {
            response.setStatus(400);
            if (exception.hasFieldErrors("email")) {
                /** 이메일이 올바른 형식이 아닙니다. */
                errorMessage = "이메일이 " + exception.getFieldError().getDefaultMessage();
            } else if (exception.hasFieldErrors("password")) {
                /** 비밀번호가 8자 이상 50자 미만이어야 합니다. */
                errorMessage = "비밀번호가 " + exception.getFieldError().getDefaultMessage();
            }
            return new ResponseDTO(400, errorMessage);
        }
        return new ResponseDTO();
    }

    @ResponseBody
    @PostMapping("/login")
    public ProfileDTO login(@RequestBody Account account, HttpServletRequest request, HttpServletResponse response) {
        log.info("email : {}, password : {}", account.getEmail(), account.getPassword());
        //토큰을 만들기 전에, 아이디가 존재하는지, 그리고 비밀번호도 맞는지부터 판별할 것.
        if (accountService.checkAccount(account.getEmail(), account.getPassword())) {
            //토큰 만들기.
            String token = jwtProvider.createToken(account);
            response.setHeader("Authorization", "bearer " + token);

            /** ++ */
            // 만든 토큰으로 DB에서 Account 가져오기
            Account getAccount = accountService.getAccountToToken("bearer " + token);

            //profileDTO 만들어오기
            ProfileDTO profile = accountService.getProfileFromEmail(account.getEmail());
            profile.setTags(accountService.getAccountTagsFromAccountToString(getAccount)); /** ++ */

            //만약 태그에 데이터가 존재한다면 상태코드 213
            if(!profile.getTags().isEmpty()) response.setStatus(213);
            // 존재하지 않으면 214
            else response.setStatus(214);
            return profile;
        }
        throw new EntityNotFoundException("login invalid");
    }

    @ResponseBody
    @PostMapping("/test")
    public ResponseDTO test() {
        log.info("heelo?");
        return new ResponseDTO(200, "hello?");
    }

    /** 프로필 정보 요청 */
    @GetMapping("/profile")
    public ProfileDTO getAccount(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        ProfileDTO profile = accountService.getProfile(header);
        return profile;
    }

    /** 프로필 수정(Birth, Phone, tags)*/
    @PostMapping("/settings/profile")
    public ProfileDTO updateProfile(@RequestBody ProfileDTO profile, HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        ProfileDTO updatedAccount = accountService.updateProfile(header, profile);
        return updatedAccount;
    }

    /* TODO PassWord UPDATE */
    /** 비밀번호 변경(Password Update) */
    @PostMapping("/settings/password")
    public ResponseDTO updatePassword(@RequestBody PasswordUpdateDTO updatePassword, HttpServletRequest request ,HttpServletResponse response) {
        String header = request.getHeader("Authorization");
        ResponseDTO responseDTO = accountService.updatePassWord(updatePassword, header);
        if(responseDTO.getStatus() != 200)
            response.setStatus(400);
        response.setStatus(200);
        return responseDTO;
    }

    /* TODO TAG UPDATE */

    /** 알림 갱신 요청 */
    @GetMapping("/notification")
    public List<NotificationDTO> getNotification(HttpServletRequest request) {
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        String token = request.getHeader("Authorization");
        Account account = accountService.getAccountToToken(token);
        // 본인에게 해당되는 Notification 모두 가져오기
        Optional<List<Notification>> allNotification = notificationRepository.findAllByAccount(account);
        // Notification이 비어있는 경우
        if(allNotification.isEmpty())
            return notificationDTOS;
        // 가져온 Notification을 모두 돌며 id, postId, PostTitle로 이루어진 DTO 생성
        allNotification.get().forEach( notification -> {
            notificationDTOS.add(new NotificationDTO(
                    notification.getId(),
                    notification.getPost().getId(),
                    notification.getPost().getTitle()));
        });
        return notificationDTOS;
    }

    /** 알림 읽음 요청 */
    @GetMapping("notification/{postId}")
    public ResponseEntity readNotification(@PathVariable Long postId, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        Account account = accountService.getAccountToToken(token);

        accountService.deleteNotice(account, postId);
        return ResponseEntity.ok().body("알림이 읽음 처리 되었습니다.");
    }
}
