package Park.gamewebpage.controller.user;

import Park.gamewebpage.dto.user.CreateUserDTO;
import Park.gamewebpage.dto.user.GetUserDTO;
import Park.gamewebpage.dto.user.UpdatePWDTO;
import Park.gamewebpage.dto.user.UpdateUserDTO;
import Park.gamewebpage.service.UserService;
import Park.gamewebpage.url.URL;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

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

    @PostMapping(URL.USER_DELETE_API)
    public String deleteUser(
            @ModelAttribute GetUserDTO getUser,Principal principal
    ) {
        GetUserDTO user
                = new GetUserDTO(userService.findByEmail(principal.getName()));
        // 비밀전호는 BCryptPasswordEncoder 로 암호화 되있기 때문에 encoder.matches가 필요하다.
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if(encoder.matches(getUser.getPassword(),user.getPassword())){
            userService.deleteById(user.getId());
            return "redirect:" + URL.USER_LOGIN_API_VIEW;
        }else {
            return "redirect:" + URL.USER_VIEW+user.getEmail();
        }
    }

    @PutMapping(URL.USER_API)
    public ResponseEntity<Void> updateUser(
            @RequestBody UpdateUserDTO userDTO,
            Principal principal
            ){
        userService.updateUser(userDTO,principal.getName());
        return ResponseEntity
                .ok()
                .build();
    }

    /**
     * 비밀번호 체크하기
     * @param userDTO 유저 dto
     * @param principal 이메일값을 가져오기 위한 객체
     * @return ResponseEntity에 결과를 알려줄 Json 오브젝트를 보내준다.
     */
    @PostMapping(URL.USER_PASSWORD_API)
    public ResponseEntity<JSONObject> checkPW(
            @RequestBody GetUserDTO userDTO,
            Principal principal
    ){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        GetUserDTO user =new GetUserDTO(userService.findByEmail(principal.getName()));
        JSONObject isTrue = new JSONObject();
        JSONArray itemList = new JSONArray();
        JSONObject items = new JSONObject();

        if(encoder.matches(userDTO.getPassword(),user.getPassword())){
            items.put("isTrue", "true");
            itemList.add(items);
            isTrue.put("items", itemList);
            return ResponseEntity
                    .ok()
                    .body(isTrue);
        }else {
            items.put("isTrue", "false");
            itemList.add(items);
            isTrue.put("items", itemList);
            return ResponseEntity
                    .ok()
                    .body(isTrue);
        }
    }

    @PatchMapping(URL.USER_PASSWORD_API)
    public ResponseEntity<JSONObject> updatePW(
            @RequestBody UpdatePWDTO updatePWDTO,
            Principal principal
            ){
        GetUserDTO userDTO
                = new GetUserDTO(userService.findByEmail(principal.getName()));
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        JSONObject jsonObject = new JSONObject();
        JSONArray itemList = new JSONArray();
        JSONObject isSuccess = new JSONObject();
        JSONObject link = new JSONObject();
        if(encoder.matches(updatePWDTO.getOldPassword(),userDTO.getPassword())){
            UpdateUserDTO updateUserDTO = new UpdateUserDTO();
            updateUserDTO.setPassword(updatePWDTO.getNewPassword());
            userService.updateUser(updateUserDTO,userDTO.getEmail());

            isSuccess.put("isSuccess","true");
            link.put("link",URL.FREE_BOARD_VIEW);

        }else {
            System.out.println("아이디 비번이 틀립니다.");
            isSuccess.put("isSuccess","false");
            link.put("link",URL.USER_VIEW+userDTO.getEmail());
        }
        itemList.add(isSuccess);
        itemList.add(link);
        jsonObject.put("jsonObject",itemList);
        return ResponseEntity
                .ok()
                .body(jsonObject);
    }
}
