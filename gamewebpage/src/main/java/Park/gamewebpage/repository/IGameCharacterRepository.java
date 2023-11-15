package Park.gamewebpage.repository;

import Park.gamewebpage.domain.GameCharacter;
import Park.gamewebpage.dto.Character.IGetGameCharacterRankDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IGameCharacterRepository extends JpaRepository<GameCharacter, Long> {
    Optional<GameCharacter> findByEmail(String email);

    Optional<GameCharacter> deleteByEmail(String email);

    @Query(value = "SELECT rank() over (order by high_score desc) as ranking,email, nickname,high_score as highscore,FROM game_characters ORDER BY high_score DESC", nativeQuery=true)
    List<IGetGameCharacterRankDTO> RankByHighScore();
}
