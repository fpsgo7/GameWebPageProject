package Park.gamewebpage.config.oauth;

import Park.gamewebpage.util.cookie.CookieUtil;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * OAuth2에 필요한 정보를 세션이 아닌 쿠키에 저장해서 쓸 수 있도록
 * 인증 요청과 관련된 상태를 저장할 저장소
 *
 * 권한 인증 흐름에서 클라이언트 요청을 유지하는데
 * 사용하는 AuthorizationRequestRepository 클래스를
 * 구현해 쿠키를 사용해 OAuth 의 정보를
 * 가져오고 저장하는 로직을 작성한다.
 *
 * 사용되는 곳으로는 SecurityConfig에서
 * .authorizationRequestRepository 메서드의 인자값으로
 * Authorization 요청과 관련된 상태 저장에 쓰이며
 *
 * OAuth2SuccessHandler 객체를 생성하는 생성자의 인자값
 * 으로 쓰인다.
 */
public class OAuth2AuthorizationRequestRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    public final static String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";// 쿠키 이름
    private final static int COOKIE_EXPIRE_SECONDS = 18000;// 쿠키 만료 시간

    /**
     * 쿠키를 통해 요청정보 가져오기
     *
     * 인자값인 HttpServletRequest request의 값을 쿠키에 담아
     * @param request the {@code HttpServletRequest}
     * @return 쿠키를 객체화한 OAuth2AuthorizationRequest 타입의 객체
     */
    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        // 해당 인자값으로 쿠키를 가져온다.
        Cookie cookie = WebUtils.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
        return CookieUtil.deserializeToObject(cookie, OAuth2AuthorizationRequest.class); 
    }

    /**
     * 인증 정보를 쿠키에 저장
     * 쿠키 추가를 통하여
     * @param authorizationRequest the {@link OAuth2AuthorizationRequest}
     * @param request the {@code HttpServletRequest}
     * @param response the {@code HttpServletResponse}
     */
    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request, HttpServletResponse response) {
        if (authorizationRequest == null) {
            removeAuthorizationRequestCookies(request, response);
            return;
        }

        CookieUtil.addCookie(response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME,
                CookieUtil.serializeToCookie(authorizationRequest),
                COOKIE_EXPIRE_SECONDS);
    }

    /**
     * 사용하지 않음
     * @param request the {@code HttpServletRequest}
     * @return
     */
    @Deprecated
    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request) {
        return null;
    }

    /**
     * 제공된 HttpServletRequest 및 HttpServletResponse와 연관된
     * OAuth2AuthorizationRequest를 제거하고 반환하며,
     * 사용할 수 없는 경우 null을 반환합니다.
     */
    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse response) {
        return this.loadAuthorizationRequest(request);
    }

    /**
     * 쿠키 삭제를 실행해준다.
     * 재생성에 사용되기도한다.
     */
    public void removeAuthorizationRequestCookies(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
    }
}
