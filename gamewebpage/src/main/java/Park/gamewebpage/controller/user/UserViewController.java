package Park.gamewebpage.controller.user;

import Park.gamewebpage.url.URL;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {

    /**
     * 로그인 뷰 열기
     */
    @GetMapping(URL.USER_LOGIN_API_VIEW)
    public String login(){
        return "user/login";// 로그인 뷰 띄우기
    }

    /**
     * 회원가입 뷰 열기
     */
    @GetMapping(URL.USER_SIGNUP_VIEW)
    public String signup(){
        return "user/signup";// 회원가입 뷰 띄우기
    }
    
}
