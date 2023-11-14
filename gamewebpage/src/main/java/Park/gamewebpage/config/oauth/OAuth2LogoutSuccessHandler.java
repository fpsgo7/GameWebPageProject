package Park.gamewebpage.config.oauth;

import Park.gamewebpage.config.token.jwt.TokenProvider;
import Park.gamewebpage.domain.RefreshToken;
import Park.gamewebpage.domain.User;
import Park.gamewebpage.repository.IRefreshTokenRepository;
import Park.gamewebpage.service.UserService;
import Park.gamewebpage.url.URL;
import Park.gamewebpage.util.cookie.CookieUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.time.Duration;

/**
 * oauth2 login이 성공할경우 실행되는 핸들러 클래스이다.
 *
 * 일반적인 로직은 동일하게 사용하고, 토큰과 관련된 작업만 추가로 처리하기
 * 위해 SimpleUrlAuthenticationSuccessHandler을 상속 받은 뒤에
 * onAuthenticationSuccess() 메서드를 오버라이드 하였다.
 *
 * 사용되는 곳은 SecurityConfig의 .successHandler 메서드이며
 * getOAuth2SuccessHandler() 메서드에서 Bean으로 객체가 생성된다.
 */
@RequiredArgsConstructor// final 변수들이 생성자의 인자값들을 받을 변수들이다.
@Component
public class OAuth2LogoutSuccessHandler implements LogoutSuccessHandler {
    public static final String REFRESH_TOKEN_COOKIE_NAME ="refresh_token";// 리프레시 토큰 
    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(0);// 리프레시 토큰 기간
    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofDays(0);// 액세스 토큰 기간
    public static final String REDIRECT_PATH = URL.FREE_BOARD_VIEW; // 리다이렉트하면 가는 경로

    private final TokenProvider tokenProvider;// 토큰 제공자
    private final IRefreshTokenRepository refreshTokenRepository;// 리프레시 토큰 저장소
    // OAuth2에 필요한 정보를 세션이 아닌 쿠키에 저장해서 쓸 수 있도록 인증 요청과 관련된 상태를 저장할 저장소
    private final OAuth2AuthorizationRequestRepository authorizationRequestRepository;
    // 유저 서비스
    private final UserService userService;

    /**
     * 저장소의 리프레시토큰을 삭제한다고 삭제되는 것은 아니니 리프레시 토큰을 만료시키는 방법을 찾아보자.
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 사용할 유저 객체 가져오기
        // 만약 OAuth2User 이 아니라면 해당 문장은 전체 실행시키지않는다.
        try{
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            User user = userService.findByEmail((String) oAuth2User.getAttributes().get("email"));

            // 1. 기간이 만료된 리프레시 토큰 생성 -> 리프레시 토큰 저장소 업데이트 -> 기존 리프레시 토큰 삭제후 쿠키에 저장
            // 만료된 리프레시 토큰으로 수정하여 토큰을 삭제한다.
            String refreshToken = tokenProvider.generateToken(user , REFRESH_TOKEN_DURATION);
            saveRefreshToken(user.getId(),refreshToken);
            addRefreshTokenToCookie(request,response,refreshToken);

            // 2. 액세스 토큰 생성 -> 패스에 액세스 토큰 추가
            String accessToken = tokenProvider.generateToken(user, ACCESS_TOKEN_DURATION);
            String redirectUrl = getRedirectUrl(accessToken);
            // 3. 인증 관련 설정값, 쿠키 제거
            // 인증 프로세스를 진행하면서 세션과 쿠키에 임시로 저장해둔 인증 관련 데이터를 제거한다.
            clearAuthenticationAttributes(request,response);
            // 4. 리다이렉트
            // 새로 생성이된 만료된 액세스 토큰으로 교체하여 토큰을 삭제한다.
            getRedirectStrategy().sendRedirect(request,response,redirectUrl);
        }catch (Exception e){
            // 해당 로그인이 Oauth2 로그인 방식이 아닐경우 리다이렉트만 실행시켜준다.
            getRedirectStrategy().sendRedirect(request,response,URL.FREE_BOARD_VIEW);
        }

    }

    /**
     * 액세스 토큰을 Uri 에 추가
     * @param accessToken 액세스 토큰 객체
     * @return 액세스 토큰 내용을 가지고 있는 Uri 반환
     */
    private String getRedirectUrl(String accessToken) {
        return UriComponentsBuilder.fromUriString(REDIRECT_PATH)
                .queryParam("token",accessToken)
                .build()
                .toString();
    }

    /**
     * 생성된 리프레시 토큰을 쿠키에 저장
     */
    private void addRefreshTokenToCookie(HttpServletRequest request,
                                         HttpServletResponse response,
                                         String refreshToken) {
        int cookieMaxAge = (int) REFRESH_TOKEN_DURATION.toSeconds();
        CookieUtil.deleteCookie(request,response,REFRESH_TOKEN_COOKIE_NAME);
        CookieUtil.addCookie(response,REFRESH_TOKEN_COOKIE_NAME,refreshToken,cookieMaxAge);
    }

    /**
     * 생성 된 리프레시 토큰을 전달받아 토큰 저장소에 저장
     * @param userId 회원의 아이디값
     * @param newRefreshToken 토큰객체
     */
    private void saveRefreshToken(Long userId, String newRefreshToken) {
        RefreshToken refreshToken = refreshTokenRepository.findByUserId(userId)
                .map(entity -> entity.updateRefreshToken(newRefreshToken))
                .orElse(new RefreshToken(userId,newRefreshToken));

        refreshTokenRepository.save(refreshToken);
    }

    /**
     * 인증 관련 설정값 쿠키 제거
     * clearAuthenticationAttributes을 오버로딩 하였다.
     */
    private void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
        authorizationRequestRepository.removeAuthorizationRequestCookies(request,response);
    }


    /***
     * AbstractAuthenticationTargetUrlRequestHandler 에서 가져옴
     * 리다이렉트와 HttpServletRequest request, HttpServletResponse response
     * 를 같이 하기 위하여 가져온 객체와 사용하기 위한 메서드이다.
     * 따로 가져온 부분이라 따로 작성하였다.
     * @return
     */
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    protected RedirectStrategy getRedirectStrategy() {
        return this.redirectStrategy;
    }
}
