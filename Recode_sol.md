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