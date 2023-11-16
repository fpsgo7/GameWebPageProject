package Park.gamewebpage.dto.hiscore;

import java.time.LocalDateTime;

public interface IGetRankByHighScoreDTO {
    int getRanking();
    String getEmail();
    String getGamecharacternickname();
    int getHighscore();
    LocalDateTime getLastedtime();
}
