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
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    public static final String REFRESH_TOKEN_COOKIE_NAME ="refresh_token";// 리프레시 토큰 
    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(7);// 리프레시 토큰 기간
    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofDays(1);// 액세스 토큰 기간
    public static final String REDIRECT_PATH = URL.USER_VIEW_OAUTH2; // 리다이렉트하면 가는 경로

    private final TokenProvider tokenProvider;// 토큰 제공자
    private final IRefreshTokenRepository refreshTokenRepository;// 리프레시 토큰 저장소
    // OAuth2에 필요한 정보를 세션이 아닌 쿠키에 저장해서 쓸 수 있도록 인증 요청과 관련된 상태를 저장할 저장소
    private final OAuth2AuthorizationRequestRepository authorizationRequestRepository;
    // 유저 서비스
    private final UserService userService;

    /**
     * OAuth 로그인이 성공할겨우 실행할 문장이다.
     * @param request the request which caused the successful authentication
     * @param response the response
     * @param authentication the <tt>Authentication</tt> object which was created during
     * the authentication process.
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {
        // 해당 로그인 방식을 알려준다.
        HttpSession session = request.getSession();
        session.setAttribute("LoginStyle","Oauth2Login");

        // authentication인증 객체에서 사용자 정보를 가져와 OAuth2User 객체를 생성
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        // oAuth2User의 이메일값으로 유저 객체를 가져온다.
        User user = userService.findByEmail((String) oAuth2User.getAttributes().get("email"));

        // 1. 리프레시 토큰 생성 -> 리프레시 토큰 저장소 저장 -> 쿠키에 저장
        String refreshToken = tokenProvider.generateToken(user , REFRESH_TOKEN_DURATION);// 리프레시 토큰 기한 적용
        saveRefreshToken(user.getId(),refreshToken);
        addRefreshTokenToCookie(request,response,refreshToken);
        // 2. 액세스 토큰 생성 -> 패스에 액세스 토큰 추가
        // http://localhost:8080/view/freeBoard?token=dsfl2k21jdsl3kf... 방식으로 만든다.
        String accessToken = tokenProvider.generateToken(user, ACCESS_TOKEN_DURATION);
        // 만약 처음으로 Oauth2 로그인을 하였다면 비밀번호 생성화면으로 이동하는 URI를 제공한다..
        // 비밀번호가 존재할경우 바로 자유게시판으로 이동하는 URI를 제공한다.
        String redirectUrl;
        if(user.getPassword()!=null){
            redirectUrl = getRedirectUrl(accessToken,REDIRECT_PATH+"?id=Password");
        }else{
            redirectUrl = getRedirectUrl(accessToken,REDIRECT_PATH+"?id=nonePassword");
        }

        // 3. 인증 관련 설정값, 쿠키 제거
        // 인증 프로세스를 진행하면서 세션과 쿠키에 임시로 저장해둔 인증 관련 데이터를 제거한다.
        //
        // 기본적으로 제공하는 메서드인 clearAuthenticationAttributes()는
        // 그대로 호출하고 removeAuthorizationRequestCoolies()
        // 를 추가로 호출해 OAuth인증을 위해 저장된 정보도 삭제한다.
        clearAuthenticationAttributes(request,response);
        // 4. 리다이렉트
        getRedirectStrategy().sendRedirect(request,response,redirectUrl);
    }
    /**
     * 인증 관련 설정값 쿠키 제거
     * clearAuthenticationAttributes을 오버로딩 하였다.
     */
    private void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        authorizationRequestRepository.removeAuthorizationRequestCookies(request,response);
    }


    /**
     * 액세스 토큰을 Uri 에 추가
     * @param accessToken 액세스 토큰 객체
     * @return 액세스 토큰 내용을 가지고 있는 Uri 반환
     */
    private String getRedirectUrl(String accessToken,String uri) {
        return UriComponentsBuilder.fromUriString(uri)
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
}
