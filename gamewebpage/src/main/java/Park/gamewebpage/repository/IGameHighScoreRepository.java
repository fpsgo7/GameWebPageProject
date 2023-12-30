package Park.gamewebpage.repository;
import Park.gamewebpage.domain.GameHighScore;
import Park.gamewebpage.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IGameHighScoreRepository extends JpaRepository<GameHighScore, Long> {
    Optional<GameHighScore> findByEmail(String email);
}