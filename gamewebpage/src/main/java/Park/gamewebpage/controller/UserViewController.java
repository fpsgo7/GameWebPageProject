package Park.gamewebpage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {

    /**
     * 로그인 뷰 열기
     */
    @GetMapping("/login")
    public String login(){
        return "user/login";// 로그인 뷰 띄우기
    }

    /**
     * 회원가입 뷰 열기
     */
    @GetMapping("/signup")
    public String signup(){
        return "user/signup";// 회원가입 뷰 띄우기
    }
    
}
