package Park.gamewebpage.repository;

import Park.gamewebpage.domain.GameCharacter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IGameCharacterRepository extends JpaRepository<GameCharacter, Long> {
    Optional<GameCharacter> findByEmail(String email);

    Optional<GameCharacter> deleteByEmail(String email);

}
