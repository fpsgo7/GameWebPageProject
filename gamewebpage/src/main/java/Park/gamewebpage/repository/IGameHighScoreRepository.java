package Park.gamewebpage.repository;

import Park.gamewebpage.domain.GameHighScore;
import Park.gamewebpage.dto.hiscore.IGetRankByHighScoreDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IGameHighScoreRepository extends JpaRepository<GameHighScore, Long> {

    @Query(value = "SELECT RANK() OVER (ORDER BY high_score desc , lasted_time) AS ranking,email, game_character_nickname AS gamecharacternickname, high_score AS highscore, lasted_time AS lastedtime FROM Game_High_Scores ORDER BY high_score DESC, lasted_time ;", nativeQuery=true)
    List<IGetRankByHighScoreDTO> RankByHighScore();
}