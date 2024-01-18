package Park.gamewebpage.controller.game;

import Park.gamewebpage.domain.GameHighScore;
import Park.gamewebpage.dto.Character.GetGameCharacterDTO;
import Park.gamewebpage.dto.user.GetUserDTO;
import Park.gamewebpage.service.GameCharacterService;
import Park.gamewebpage.service.GameHighScoreService;
import Park.gamewebpage.service.UserService;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class GameController {
    // http 주소가 유출될경우 아무나 주소로 요청할 수 있기 때문에
    // 로그인할 때 유니티가 웹서버로부터 인증용 비번을 받고
    // 유니티가 요청할때 인증용비번을 같이 보내며 요청한다.
    private final String UNITY_AUTHENTICATION = "a8r5h4m2vc3sd5y4t5k3gh21vg";

    private final UserService userService;
    private final GameCharacterService gameCharacterService;
    private final GameHighScoreService gameHighScoreService;

    // 암호화와 복호화를 위해 사용한다.
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    JSONObject jsonObject = new JSONObject();

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
        String email = loginInfo.get("email").toString();
        String password = loginInfo.get("password").toString();
        GetUserDTO user = null;
        try{
            user = new GetUserDTO(userService.findByEmail(email));
        }catch (Exception e) {
            // 아이디가 잘못됬을 경우
            jsonObject.put("isUser", "false");
            return ResponseEntity
                    .ok()
                    .body(jsonObject);
        }
        // 비밀전호는 BCryptPasswordEncoder 로 암호화 되있기 때문에 encoder.matches가 필요하다.
        // 비밀번호 확인
        if(encoder.matches(password,user.getPassword())){
            // 로그인이 성공한다면!
            jsonObject.put("isUser","true");
            jsonObject.put("UNITY_AUTHENTICATION",encoder.encode(UNITY_AUTHENTICATION));// 유니티 인증 비번 보내기
            inputUserInfoJson(user.getEmail(),user.getNickname());
            inputGameCharacterInfoJson(user.getEmail());
        }else {
            jsonObject.put("isUser", "false");
        }

        return ResponseEntity
                .ok()
                .body(jsonObject);
    }
    private void inputUserInfoJson(String email, String userNickname ){
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

    /**
     * 유니티 에서의 게임 캐릭터 생성
     * @param gameCharacterInfo
     * @return
     */
    @PostMapping("/game/gameCharacter")
    public ResponseEntity<JSONObject> createGameCharacter(
            @RequestBody HashMap<String, Object> gameCharacterInfo,
            HttpServletRequest request
    ){
        jsonObject.clear();
        String email = gameCharacterInfo.get("email").toString();
        String nickname = gameCharacterInfo.get("nickname").toString();

        if(!UnityAuthentication(request.getParameter("unityAuthenticaiton").toString()))
            return ResponseEntity.ok().body(null);
           
        try{
            GetGameCharacterDTO gameCharacter
                    =new GetGameCharacterDTO(gameCharacterService
                    .createGameCharacter(email, nickname, 0L));
            gameHighScoreService.createGameHighScore(
                    GameHighScore.builder()
                            .email(email)
                            .gameCharacterNickname(nickname)
                            .highScore(0L)
                            .lastedTime(LocalDateTime.now())
                            .build()

            );

            jsonObject.put("isGameCharacter","true");
            jsonObject.put("userEmail",gameCharacter.getEmail());
            jsonObject.put("gameCharacterNickname",gameCharacter.getNickname());
            jsonObject.put("gameCharacterHighScore", gameCharacter.getHighScore());
        }catch (Exception e){
            jsonObject.put("isGameCharacter","false");
        }
        return ResponseEntity
                .ok()
                .body(jsonObject);
    }

    /**
     * 게임 랭크 전체 정보를 가져와 반환한다.
     * @return
     */
    @GetMapping("/game/gameHighScores")
    public ResponseEntity<List<GameHighScore>> getGameCharacterRankList(
            HttpServletRequest request
    ){

        if(!UnityAuthentication(request.getParameter("unityAuthenticaiton").toString()))
            return ResponseEntity.ok().body(null);

        Sort sort = Sort.by(
                Sort.Order.desc("highScore"),
                Sort.Order.asc("lastedTime")    );
        List<GameHighScore> gameHighScores
                = gameHighScoreService.getGameCharacterRanks(sort);

        return ResponseEntity
                .ok()
                // 자동으로 json 방식으로 보내준다.
                .body(gameHighScores);
    }

    /**
     * 게임 랭크 최고점수를 수정한다.
     * @return
     */
    @PostMapping("/game/gameHighScore")
    public ResponseEntity<GameHighScore>  setGameHighScore(
            @RequestBody HashMap<String, Object> gameHighScoreInfo,
            HttpServletRequest request
    ){
        if(!UnityAuthentication(request.getParameter("unityAuthenticaiton").toString()))
            return ResponseEntity.ok().body(null);

        GameHighScore gameHighScore = null;
        String email = gameHighScoreInfo.get("email").toString();
        String newScore = gameHighScoreInfo.get("newScore").toString();
        try{
            gameHighScoreService.setGameHighScore(email,newScore);
            gameCharacterService.updateGameCharacterHighScore(email,newScore);
            gameHighScore = gameHighScoreService.getGameCharacterRank(email);
        }catch (Exception e){

        }

        return ResponseEntity
                .ok()
                // 자동으로 json 방식으로 보내준다.
                .body(gameHighScore);
    }

    public boolean UnityAuthentication(String unityAuthentication){
        // 게임이 실행중인 유니티에서 요청이 온것인지 확인하기
        if(encoder.matches(UNITY_AUTHENTICATION,unityAuthentication)){
            return true;
        }
        return  false;
    }
}
