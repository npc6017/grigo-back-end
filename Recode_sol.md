# GRIGO 프로젝트 기록 (Sol)
> ## 7월 7일 (수)
> > ### 구현   
> > - 회원 가입  
> > - 비밀번호 암호화  
> > - 중복 메일 가입 검증  
> > - 중복 학번 가입 검증
> > - 이메일 형식 및 비밀번호 길이 제한
> 
> > - config 패키지  
> AppConfig 작성 : 비밀번호 암호화를 위한 빈 등록
> SecurityConfig 작성 : 스프링 시큐리티 설정 커스터마이징
> 
> > - controller 패키지  
> AccountController 작성 : 가입 메서드 작성 및 회원가입 검증 Binder 작성  
> MainController 작성
> 
> > - domain/ account 패키지  
> Account 모델링 : Account 모델과 임시 저장소 인터페이스와 구현체 작성 및 회원가입을 위한 Form 작성.
>
> > - service 패키지   
> AccountService 작성 : Join 구현
>
> > - validator 패키지  
> SignUpValidator 작성 : 이메일, 학번 중복 여부 검증 작성
> 
>
> ## 7월 11일 (일)
> > ### 구현
> > - 회원 가입 응답 수정
> > - MethodArgumentNotValidException 핸들러 작성  
> 
> > - controller 패키지  
        AccountController 수정 : join의 응답을 JoinDTO로 응답.  
> 응답은 가입 검증에 걸리게 되면 JoinDTO의 상태 코드를 400(Bad Request), 에러 메세지를 담아 응답한다.  
       검증에 통과하면 상태 코드 200과 Success메세지를 담아 응답한다.  
> response 상태 코드도 같이 설정하지만, 클라이언트에서 확인이 안된다고 하여 DTO에 추가로 넣어 응답한다.  
> MethodArgumentNotValidException 핸들러 작성 : 작성한 검증 조건(객체의 @NotBlank 등)에 걸리게 되면, 예외에 걸리게 되어 이에 대한 처리 핸들러가 필요하다. 따라서 @ExceptionHandler(MethodArgumentNotValidException.class) 어노테이션을 추가한 핸들러 메서드를 작성. 
> 
> > - domain/ account 패키지  
       JoinDTO 작성 : 응답의 상태와 에러 메세지를 담는 DTO. 응답 상태를 넣은 이유는 클라이언트에서 응답 상태 확인이 불가능하다 하여 추가.
>
> ## 7월 12일 (월)
> > ### 구현
> > - 프로필 정보 응답
> > - 유저 정보 수정(Birth, Phone)
>
> > - controller 패키지  
      AccountController 수정 :  프로필 정보 응답 맵핑 메서드 및 프로필 수정 맵핑 메서드 작성.      
       request를 서비스의 메서드로 넘겨 반환받은 ProfileDTO를 클라이언트에 응답해준다.
>
> > - domain/ account 패키지  
      ProfileDTO 작성 : Account에서 Passwor와 id를 제외한 보여지는 프로필 정보를 담당하는 DTO 작성
>
> > - service 패키지  
      AccountService 추가 작성 : 매 요청마다 헤더의 Token으로 유저를 찾아야하기 때문에 Token으로 유저 반환받는 getAccountToToken메서드 작성, account를 통해 profileDTO 생성 메서드 작성.
      유저 정보 반환 메서드, (Birth, Phone)업데이트 메서드 작성.
> ## 7월 13일 (화)
> > ### 구현
> > - 패스워드 변경
>
> > - controller 패키지  
      AccountController 수정 : current, new, confirm 총 3개의 비밀번호를 받아와 AccountService의 updatePassword메서드를 통한 결과 응답.
>
> > - domain/ account 패키지  
      PasswordUpdateDTO 작성 : current, new, confirm 총 3개의 비밀번호를 갖는 DTO 작성.
>
> > - service 패키지  
      AccountService 수정 : 요청받은 3가지의 비밀번호를 통해 currentPassword 확인, 새로운 비밀번호의 중복 여부를 확인하여 비밀번호 변경 또는 불가능 사유 ResponseDTO 생성하여 반환.
      > ## 7월 13일 (화)
> ## 7월 20일 (수)
> > ### 구현
> > - Account - Comment, Post | Post - Comment 관계 설정
> > - Comment Add Update Delete 구현
>
> > - controller 패키지  
> >    - CommentController 수정
> >     1. Comment Add : 유저 확인 -> 게시글 유무 확인 -> 댓글 추가.
> >     2. Comment Update : 유저 확인 ->  댓글 유무 확인 -> 유저 댓글 소유권 확인 -> 댓글 수정
> >     3. Comment Delete : 유저 확인 -> 댓글 유무 확인 -> 유저 댓글 소유권 확인 -> 댓글 삭제
>
> > - domain  
> >    - Account  
> >     1. Account - Post | 1대다 관계 설정
> >     2. Account - Comment | 1대다 관계 설정
> >    - Comment 
> >     1. Comment 도메인 작성
> >     2. Comment - Account | 다대1 관계 설정
> >     3. Comment - Post | 다대1 관계 설정  
> >     3. CommentDTO 작성
> >    - Post
> >     1. Post - Account 다대1 관계 설정
> >     2. Post - Comment | 1대다 관계 설정
> ## 7월 26일 (월)
> > ### 구현
> > - Account - Tag 다대다 관계 설정  
> > - Account - Post 다대다(Notification) 관계 설정
> > - Notification 기능 구현
>
> > - controller 패키지
> >     - AccountController  
> >         1. Notification 갱신 및 읽음 요청 처리 구현 
> >     - PostController  
> >         1. savePost 메서드에서 태그가 존재하는 경우, 알림 생성 로직 추가 
> > - domain 패키지  
> >     - Account, AccountTag, Notification, Tag
> >         1. Account - Post -> Notification | 다대다 관계 설정
> >         2. Account - Tag 다대다 관계 설정
> >         3. NotificationDTO(id, postId, tag(string)) 생성  
> > - service 패키지
> >     - AccountService
> >         1. 알림 생성 setNotification 메서드 구현
> >         2. 알림 읽음 처리 deleteNotice 메서드 구현
> >     - PostService
> >         1. savePost 메서드의 응답 타입 void -> Post로 변경하여 사용  
> >     - TagService  
> >         1. saveTags 로직 일부 변경
