package Park.gamewebpage.service;

import Park.gamewebpage.domain.GameHighScore;
import Park.gamewebpage.repository.IGameHighScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
     * 저장소의 findAll(Pageable객체 ) 메서드와 인자값을할경우
     * List 가 아닌 Page 로 반환된다.
     * 그래서 해당 메서드의 클래스는 Page이다.
     * @return 게임 캐릭터 점수 정렬 조회 결과
     */
    public Page<GameHighScore> getGameCharacterRankPageable(Pageable pageable){
        return iGameHighScoreRepository.findAll(pageable);
    }

    public List<GameHighScore> getGameCharacterRanks(Sort sort){
        return  iGameHighScoreRepository.findAll(sort);
    }

    public GameHighScore createGameHighScore(GameHighScore gameHighScore){
        return iGameHighScoreRepository.save(gameHighScore);
    }
}
