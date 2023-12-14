package Park.gamewebpage.config;

import Park.gamewebpage.url.URL;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 로그인이 성공하면 실행할
 * 대상들이있다.
 * 해당 로그인 방식이 FormLogin 임을 알린다.
 */
@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();
        session.setAttribute("LoginStyle","FormLogin");
        getRedirectStrategy().sendRedirect(request,response, URL.FREE_BOARD_VIEW);
    }
}
