package Park.gamewebpage.controller.game;

import Park.gamewebpage.dto.Character.GetGameCharacterDTO;
import Park.gamewebpage.dto.user.GetUserDTO;
import Park.gamewebpage.service.GameCharacterService;
import Park.gamewebpage.service.UserService;
import Park.gamewebpage.url.URL;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RequiredArgsConstructor
@RestController
public class GameController {
    private final UserService userService;
    private final GameCharacterService gameCharacterService;
    JSONObject jsonObject = new JSONObject();

    /**
     * 유니티 에서의 http test를 위한
     * 컨트롤러
     * @return
     */
    @GetMapping("/game/test")
    public ResponseEntity<JSONObject> getTest(){

        System.out.println("get 요청이 왔습니다.");
        jsonObject.clear();
        jsonObject.put("true","ture");
        return ResponseEntity
                .ok()
                .body(jsonObject);
    }

    /**
     * 유니티 에서의 http post
     * test를 위한 컨트롤러
     */
    @PostMapping("/game/test")
    public ResponseEntity<JSONObject> postTest(
            @RequestBody HashMap<String, Object> map
    ){
        System.out.println(map);
        jsonObject.clear();
        jsonObject.put("true","ture");
        return ResponseEntity
                .ok()
                .body(jsonObject);
    }

    /**
     * 유니티 에서의 로그인
     * @param loginInfo
     * @return
     */
    @PostMapping("/game/user")
    public ResponseEntity<JSONObject> login(
            @RequestBody HashMap<String, Object> loginInfo
    ){
        jsonObject.clear();
        System.out.println("입력된 이메일값"+loginInfo.get("email").toString());
        System.out.println("입력된 비밀번호 값"+loginInfo.get("password").toString());
        GetUserDTO user = null;
        try{
            user = new GetUserDTO(userService.findByEmail(loginInfo.get("email").toString()));
        }catch (Exception e){
            // 아이디가 잘못됬을 경우
            jsonObject.put("isUser", "false");
            return ResponseEntity
                    .ok()
                    .body(jsonObject);
        }

        // 비밀전호는 BCryptPasswordEncoder 로 암호화 되있기 때문에 encoder.matches가 필요하다.
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        // 비밀번호 확인
        if(encoder.matches(loginInfo.get("password").toString(),user.getPassword())){
            // 로그인이 성공한다면!
            jsonObject.put("isUser","true");
            inputUserInfoJson(user.getId(),user.getEmail(),user.getNickname());
            inputGameCharacterInfoJson(user.getEmail());
        }else {
            jsonObject.put("isUser", "false");
        }


        return ResponseEntity
                .ok()
                .body(jsonObject);
    }
    private void inputUserInfoJson(Long userId,String email, String userNickname ){
        jsonObject.put("userEmail",email);
        jsonObject.put("userNickname",userNickname);
    }
    private  void inputGameCharacterInfoJson(String email){
        GetGameCharacterDTO gameCharacter;
        try{
            gameCharacter = new GetGameCharacterDTO(gameCharacterService.findByEmail(email));
            jsonObject.put("isGameCharacter","true");
            jsonObject.put("gameCharacterNickname",gameCharacter.getNickname());
            jsonObject.put("gameCharacterHighScore", gameCharacter.getHighScore());
        }catch (Exception e){
            jsonObject.put("isGameCharacter","false");
        }
    }
}
