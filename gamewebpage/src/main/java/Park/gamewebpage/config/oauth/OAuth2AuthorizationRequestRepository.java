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
 */
public class OAuth2AuthorizationRequestRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    public final static String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";
    private final static int COOKIE_EXPIRE_SECONDS = 18000;

    /**
     * 쿠키를 객체화 해서 가져오기
     * @param request the {@code HttpServletRequest}
     * @return 쿠키를 객체화한것
     */
    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
        return CookieUtil.deserializeToObject(cookie, OAuth2AuthorizationRequest.class);
    }

    /**
     * 쿠키 추가하기
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

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse response) {
        return this.loadAuthorizationRequest(request);
    }

    public void removeAuthorizationRequestCookies(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
    }
}
