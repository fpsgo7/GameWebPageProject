package Park.gamewebpage.dto.Character;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateGameCharacterHighScoreDTO {
    private Long highScore;

    public UpdateGameCharacterHighScoreDTO(Long highScore){
        this.highScore =highScore;
    }
}
