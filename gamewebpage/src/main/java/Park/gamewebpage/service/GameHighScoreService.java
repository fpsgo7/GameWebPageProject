package Park.gamewebpage.service;

import Park.gamewebpage.dto.hiscore.IGetRankByHighScoreDTO;
import Park.gamewebpage.repository.IGameHighScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * 게임 최고점수 서비스이다.
 */
@RequiredArgsConstructor
@Service
public class GameHighScoreService {
    private final IGameHighScoreRepository iGameHighScoreRepository;
    // READ PART
    /**
     * 게임 캐릭터 순위 대로 가져온다.
     * @return 게임 캐릭터 점수 정렬 조회 결과
     */
    public List<IGetRankByHighScoreDTO> getGameCharacterRankList(){
        return iGameHighScoreRepository.RankByHighScore();
    }
}
