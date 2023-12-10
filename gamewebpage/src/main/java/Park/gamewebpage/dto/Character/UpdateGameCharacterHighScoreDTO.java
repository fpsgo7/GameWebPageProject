package Park.gamewebpage.dto.Character;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateGameCharacterHighScoreDTO {
    private int highScore;

    public UpdateGameCharacterHighScoreDTO(int highScore){
        this.highScore =highScore;
    }
}
