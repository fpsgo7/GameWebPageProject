package Park.gamewebpage.controller;

import Park.gamewebpage.dto.freeboardDTO.user.CreateUserDTO;
import Park.gamewebpage.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class UserApiController {
    private final UserService userService;

    @PostMapping("/user")
    public String signup(CreateUserDTO createUserDTO){
        userService.createUser(createUserDTO); // 회원가입 서비스
        return "redirect:/login"; // 회원가입이 완료된 이후에 로그인 페이지로 이동
    }
}
