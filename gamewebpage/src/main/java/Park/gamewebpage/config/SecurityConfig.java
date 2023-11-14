package Park.gamewebpage.config;

import Park.gamewebpage.config.oauth.OAuth2AuthorizationRequestRepository;
import Park.gamewebpage.config.oauth.OAuth2LogoutSuccessHandler;
import Park.gamewebpage.config.oauth.OAuth2SuccessHandler;
import Park.gamewebpage.config.oauth.OAuth2UserService;
import Park.gamewebpage.config.token.TokenAuthenticationFilter;
import Park.gamewebpage.config.token.jwt.TokenProvider;
import Park.gamewebpage.repository.IRefreshTokenRepository;
import Park.gamewebpage.service.UserService;
import Park.gamewebpage.url.URL;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.Filter;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

/**
 * 스프링 시큐리티 설정
 * 빈등록을 활용하여 객체를 생성 반환받아 사용한다.
 */
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final LoginFailureHandler loginFailureHandler;
    private final OAuth2UserService oAuth2UserService;
    private final TokenProvider tokenProvider;
    private final IRefreshTokenRepository refreshTokenRepository;
    private final UserService userService;

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
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
            throws Exception{
        // 기능 테스트를 위해 비활성화 시킨다.
        httpSecurity.csrf().disable();
        // 세션 제어를 위해사용한다.
        // OAuth2로그인 에서는 세션을 사용하지 않으며
        // 기존 로그인 방식에서만 사용한다.
        httpSecurity.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

        // 만들어둔 토큰 필터 클래스 TokenAuthenticationFilter  을
        // 활용하여 헤더를 확인할 커스텀 필터를 추가한다.
        httpSecurity.addFilterBefore(getTokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        // 토큰 재발급 URL은 인증 없이 접근 가능하도록 설정한다.
        // 그외에 API 경로로 시작하는 URL은 전부 인증이 필요하게한다.
        httpSecurity.authorizeRequests()
                .antMatchers(URL.TOKEN_API).permitAll()
                .antMatchers(URL.API+"/**").authenticated()
                .anyRequest().permitAll();
        // /api로 시작하는 url인 경우 401 상태 코드를 반환하도록 예외처리
        httpSecurity.exceptionHandling()
                .defaultAuthenticationEntryPointFor(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                        new AntPathRequestMatcher(URL.API+"/**"));


        /* oauth2Login() 설정 */
        // OAuth2에 필요한 정보를 세션이 아닌 쿠키에 저장해서
        // 쓸 수 있도록 인증 요청과 관련된 상태를 저장할 저장소를 설정한다.
        // 인증 성공 시 실행할 핸들러도 설정한다.
        httpSecurity.oauth2Login()
                .loginPage(URL.USER_LOGIN_API_VIEW)
                .authorizationEndpoint()
                // Authorization 요청과 관련된 상태 저장
                .authorizationRequestRepository(getOAuth2AuthorizationRequestRepository())
                .and()
                // 인증 성공 시 실행할 핸들러
                .successHandler(getOAuth2SuccessHandler())
                .userInfoEndpoint()
                .userService(oAuth2UserService);
        /* 기본 로그인 설정*/
        httpSecurity.formLogin()
                .loginPage(URL.USER_LOGIN_API_VIEW)
                .defaultSuccessUrl(URL.FREE_BOARD_VIEW)
                .failureHandler(loginFailureHandler);
        /* 로그 아웃 설정*/
        httpSecurity.logout()
                .logoutSuccessHandler(getOAuth2LogoutSuccessHandler())
                .invalidateHttpSession(true);

        return httpSecurity.build();

    }

    /**
     * @return OAuth2SuccessHandler 객체
     */
    @Bean
    public OAuth2SuccessHandler getOAuth2SuccessHandler() {
        return new OAuth2SuccessHandler(tokenProvider,
                refreshTokenRepository,
                getOAuth2AuthorizationRequestRepository(),
                userService);
    }

    /**
     * @return OAuth2AuthorizationRequestRepository 객체
     */
    @Bean
    public OAuth2AuthorizationRequestRepository getOAuth2AuthorizationRequestRepository() {
        return new OAuth2AuthorizationRequestRepository();
    }

    /**
     * tokenProvider 을 인자값으로 하여
     * TokenAuthenticationFilter 객체를 생성한 후 반환한다.
     */
    @Bean
    public Filter getTokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenProvider);
    }

    /**
     * 패스워드 인코더로 사용할 빈 등록
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return  new BCryptPasswordEncoder();
    }

    /**
     * 로그아웃 성공 핸들러
     */
    @Bean
    public OAuth2LogoutSuccessHandler getOAuth2LogoutSuccessHandler(){
        return  new OAuth2LogoutSuccessHandler(tokenProvider, refreshTokenRepository,
                getOAuth2AuthorizationRequestRepository(),
                userService);
    }
}
