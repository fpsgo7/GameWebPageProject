package Park.gamewebpage.controller.user;

import Park.gamewebpage.dto.Character.GetGameCharacterDTO;
import Park.gamewebpage.dto.user.CreateUserFormDTO;
import Park.gamewebpage.dto.user.GetUserDTO;
import Park.gamewebpage.service.UserService;
import Park.gamewebpage.url.URL;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class UserViewController {
    private final UserService userService;

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
    public String signup(@ModelAttribute("createUserFormDTO")  CreateUserFormDTO createUserFormDTO){
        return "user/signup";// 회원가입 뷰 띄우기
    }

    /**
     * 회원 가입 동의 화면 팝업창 띄우기
     */
    @GetMapping(URL.USER_SIGNUP_POPUP_VIEW)
    public String sighnupPopup(){
        return "user/signupPopup";
    }

    /**
     * 회원정보 뷰 열기
     */
    @GetMapping(URL.USER_VIEW_BY_EMAIL)
    public String getGameCharacterView(
            @PathVariable String email, Model model
    ){
        GetUserDTO user = new GetUserDTO(userService.findByEmail(email));
        model.addAttribute("user", user);
        return "user/userinfo";
    }

    /**
     * Oauth2 로그인 중 뷰 열기
     */
    @GetMapping(URL.USER_VIEW_OAUTH2)
    public String loginningOauth2View(){
        return "user/oauth2loginning";
    }
    /**
     * Oauth2 유저 비밀번호 생성
     * 뷰 열기
     */
    @GetMapping(URL.USER_VIEW_OAUTH2_PW)
    public String createOauth2PWView(
    ){
        return "user/oauth2usercreatepw";
    }


}
