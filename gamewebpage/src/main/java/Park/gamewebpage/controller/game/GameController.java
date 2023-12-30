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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class GameController {
    private final UserService userService;
    private final GameCharacterService gameCharacterService;
    private final GameHighScoreService gameHighScoreService;
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
        if(encoder.matches(password,user.getPassword())){
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

    /**
     * 유니티 에서의 게임 캐릭터 생성
     * @param gameCharacterInfo
     * @return
     */
    @PostMapping("/game/gameCharacter")
    public ResponseEntity<JSONObject> createGameCharacter(
            @RequestBody HashMap<String, Object> gameCharacterInfo
    ){
        jsonObject.clear();
        String email = gameCharacterInfo.get("email").toString();
        String nickname = gameCharacterInfo.get("nickname").toString();

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
    public ResponseEntity<List<GameHighScore>> getGameCharacterRankList(){
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
            @RequestBody HashMap<String, Object> gameHighScoreInfo
    ){
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
}
