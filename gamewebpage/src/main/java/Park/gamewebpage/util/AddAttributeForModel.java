package Park.gamewebpage.util;

import Park.gamewebpage.dto.user.GetUserDTO;
import Park.gamewebpage.service.UserService;

import org.springframework.ui.Model;

import java.security.Principal;

public class AddAttributeForModel {
    /**
     * 헤더 공통 필요 모델요소
     */
    public static void getUserInfo(
            UserService userService,
            Principal principal, Model model
    ){
        try{
            // 회원가입이 되있는 경우
            GetUserDTO user = new GetUserDTO(userService.findByEmail(principal.getName()));
            model.addAttribute("user", user);
        }catch (Exception e){
        }

    }
}
