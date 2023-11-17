package Park.gamewebpage.repository;

import Park.gamewebpage.domain.GameHighScore;
import Park.gamewebpage.dto.hiscore.IGetRankByHighScoreDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IGameHighScoreRepository extends JpaRepository<GameHighScore, Long> {
}