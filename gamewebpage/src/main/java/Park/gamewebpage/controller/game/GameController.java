package Park.gamewebpage.controller.game;

import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RequiredArgsConstructor
@RestController
public class GameController {

    JSONObject jsonObject = new JSONObject();

    /**
     * 유니티 에서의 http test를 위한
     * 컨트롤러
     * @return
     */
    @GetMapping("/game/test")
    public ResponseEntity<JSONObject> getTest(){

        System.out.println("get 요청이 왔습니다.");
        jsonPut("true");
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
        jsonPut("true");
        return ResponseEntity
                .ok()
                .body(jsonObject);
    }

    /**
     * json 오브젝트를 초기화한 후
     * 인자 값을 받아 Json에 값을 넣어준다.
     */
    private void jsonPut(String isSuccess){
        jsonObject.clear();
        jsonObject.put("isSuccess",isSuccess);
    }
}
