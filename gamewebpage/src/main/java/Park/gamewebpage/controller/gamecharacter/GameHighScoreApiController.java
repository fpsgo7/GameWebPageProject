package Park.gamewebpage.controller.gamecharacter;

import Park.gamewebpage.domain.GameHighScore;
import Park.gamewebpage.service.GameHighScoreService;
import Park.gamewebpage.url.URL;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public ResponseEntity<List<GameHighScore>> getGameCharacterRankList(){
        Sort sort = Sort.by(
                Sort.Order.desc("highScore"),
                Sort.Order.asc("lastedTime")    );
        Pageable pageable =
                PageRequest.of(0, 10, sort);

        List<GameHighScore> gameCharacterRankList
                = gameHighScoreService.getGameCharacterRankPageable(pageable).toList();

        return ResponseEntity
                .ok()
                .body(gameCharacterRankList);
    }

}
