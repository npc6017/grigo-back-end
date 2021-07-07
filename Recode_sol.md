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