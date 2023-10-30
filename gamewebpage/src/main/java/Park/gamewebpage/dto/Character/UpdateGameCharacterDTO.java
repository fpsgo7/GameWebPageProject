package Park.gamewebpage.dto.Character;

import Park.gamewebpage.domain.GameCharacter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateGameCharacterDTO {
    private String email;
    private int highScore;
    private String nickName;

    public UpdateGameCharacterDTO(GameCharacter gameCharacter){
        this.email = gameCharacter.getEmail();
        this.highScore = gameCharacter.getHighScore();
        this.nickName = gameCharacter.getNickname();
    }
}
