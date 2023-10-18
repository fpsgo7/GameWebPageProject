package Park.gamewebpage.controller;

import Park.gamewebpage.dto.freeboardDTO.user.CreateUserDTO;
import Park.gamewebpage.service.UserService;
import Park.gamewebpage.url.URL;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Controller
public class UserApiController {
    private final UserService userService;

    /**
     * 회원가입을 담당하는 메서드
     */
    @PostMapping(URL.USER_SIGNUP_API)
    public String signup(CreateUserDTO createUserDTO){
        userService.createUser(createUserDTO); // 회원가입 서비스
        return "redirect:"+URL.USER_LOGIN_API_VIEW; // 회원가입이 완료된 이후에 로그인 페이지로 이동
    }

    /**
     * 로그아웃을 위한 메서드이다.
     * SecurityContextLogoutHandler 의 logout을 통하여 로그아웃하여
     * 스프링 시큐리티를 활용한다.
     */
    @GetMapping(URL.USER_LOGOUT_API)
    public String logout(HttpServletRequest request, HttpServletResponse response){
        new SecurityContextLogoutHandler().logout(request,response
        , SecurityContextHolder.getContext().getAuthentication());
        return "redirect:"+URL.USER_LOGIN_API_VIEW;
    }
}
