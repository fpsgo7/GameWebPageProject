package Park.gamewebpage.config;

import Park.gamewebpage.service.UserDetailService;
import Park.gamewebpage.url.URL;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

/**
 * 스프링 시큐리티 설정
 */
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final LoginFailureHandler loginFailureHandler;

    /**
     * 스프링 시큐리티에 적용하지 않을 대상을
     * 입력한다.
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring()
                // H2 데이터베이스 콘솔
                .requestMatchers(toH2Console())
                // static 경로에 있는 파일들
                .antMatchers("/static/**");
    }

    /**
     * 특정 HTTP 요청에 대한
     * 웹 기반 보안 구성 설정한다.
     * "/login","/signup","/user" 왜에 나머지
     * URL은 인증후 사용가능하며
     * 로그인 url 은 /login 이며 post로 오면 실행
     * 로그 아웃 후에는 /login url을 get으로 실행하며
     * 로그아웃 하면 세션은 삭제된다.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
            throws Exception{
        return httpSecurity
                // HTTP 요청에 대한 인가 설정 구성 시작
                .authorizeHttpRequests()
                // 인증 인가 설정
                // "/login","/signup","/user" 로 시작하는 url은 모두에게 허가
                .antMatchers(
                        URL.USER_LOGIN_API_VIEW,
                        URL.USER_SIGNUP_VIEW,
                        URL.USER_SIGNUP_API).permitAll()
                // 그외 나머지는 인증후 이용가능하다.
                .anyRequest().authenticated()
                .and()
                //폼기반 로그인설정
                .formLogin()
                .loginPage(URL.USER_LOGIN_API_VIEW)
                .failureHandler(loginFailureHandler) // 로그인 실패 핸들러
                .defaultSuccessUrl(URL.FREE_BOARD_VIEW)
                .and()
                // 로그아웃 설정
                .logout()
                .logoutSuccessUrl(URL.USER_LOGIN_API_VIEW)
                .invalidateHttpSession(true)// 로그아웃 하면 세션 전체 삭제
                .and()
                //csrf 공격 방지 비활성화  (테스트 동안만 할것)
                .csrf()
                .disable()
                .build();
    }

    /**
     * 인증 관리자를 설정한다.
     */
    @Bean
    public AuthenticationManager authenticationManager(
            HttpSecurity httpSecurity,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            UserDetailService userDetailService
    )throws Exception{
        return httpSecurity
                .getSharedObject(AuthenticationManagerBuilder.class)
                // 사용자 서비스 설정
                .userDetailsService(userDetailService)// 사용자 정보를 가져올 서비스 UserDetailsService 을 상속받은 클래스
                .passwordEncoder(bCryptPasswordEncoder) // 페스워드 인코더
                .and()
                .build();
    }

    /**
     * 패스워드 인코더로 사용할 빈 등록
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return  new BCryptPasswordEncoder();
    }
}
