package site.grigo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity // 내가 직접 스프링 시큐리티 설정을 하겠다는 어노테이션
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 스프링 시큐리티 설정
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // csrf 끄기
        httpSecurity.csrf().disable();
    }
}
