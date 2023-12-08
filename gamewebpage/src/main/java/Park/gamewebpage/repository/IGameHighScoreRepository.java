package Park.gamewebpage.repository;
import Park.gamewebpage.domain.GameHighScore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGameHighScoreRepository extends JpaRepository<GameHighScore, Long> {
}