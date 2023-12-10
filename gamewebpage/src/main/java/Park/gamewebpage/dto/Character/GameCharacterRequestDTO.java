package Park.gamewebpage.dto.Character;

import lombok.*;

@NoArgsConstructor // 매계변수 없는 기본 생성자
@Getter
@Setter
public class GameCharacterRequestDTO {
    private int highScore;
    private String nickname;

    @Builder
    public GameCharacterRequestDTO(int highScore, String nickname){
        this.highScore = highScore;
        this.nickname = nickname;
    }
}
