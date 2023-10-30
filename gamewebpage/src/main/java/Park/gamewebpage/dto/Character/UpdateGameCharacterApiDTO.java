package Park.gamewebpage.dto.Character;

import Park.gamewebpage.domain.GameCharacter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateGameCharacterApiDTO {
    private String email;
    private int highScore;
    private String nickname;

    public UpdateGameCharacterApiDTO(GameCharacter gameCharacter){
        this.email = gameCharacter.getEmail();
        this.highScore = gameCharacter.getHighScore();
        this.nickname = gameCharacter.getNickname();
    }
}
