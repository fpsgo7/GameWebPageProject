package Park.gamewebpage.dto.Character;

import Park.gamewebpage.domain.GameCharacter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetGameCharacterDTO {
    private final String email;
    private final int highScore;
    private final String nickname;

    public GetGameCharacterDTO(GameCharacter gameCharacter){
        this.email = gameCharacter.getEmail();
        this.highScore = gameCharacter.getHighScore();
        this.nickname = gameCharacter.getNickname();
    }
}
