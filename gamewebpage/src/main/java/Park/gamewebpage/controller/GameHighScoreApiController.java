package Park.gamewebpage.controller;

import Park.gamewebpage.dto.hiscore.IGetRankByHighScoreDTO;
import Park.gamewebpage.service.GameHighScoreService;
import Park.gamewebpage.url.URL;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class GameHighScoreApiController {
    private  final GameHighScoreService gameHighScoreService;

    /**
     * 테스트용
     * 게임 케릭터 순위 전체 조회 메서드이다.
     * @return 게임 캐릭터 리스트
     */
    @GetMapping(URL.GAME_CHARACTER_RANK_API)
    public ResponseEntity<List<IGetRankByHighScoreDTO>> getGameCharacterRankList(){
        List<IGetRankByHighScoreDTO> gameCharacterRankList
                = gameHighScoreService.getGameCharacterRankList();

        return ResponseEntity
                .ok()
                .body(gameCharacterRankList);
    }

}
