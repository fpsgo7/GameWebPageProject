package Park.gamewebpage.dto.Character;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateGameCracterDTO {

    private String email;
    private String nickname;
    // 점수는 0으로 시작하기에
    private int highScore =0 ;
}
