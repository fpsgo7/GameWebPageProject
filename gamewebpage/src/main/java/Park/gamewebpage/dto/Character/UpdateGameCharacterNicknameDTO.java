package Park.gamewebpage.dto.Character;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 전체 필드변수 생성자
@Getter
@Setter
public class UpdateGameCharacterNicknameDTO {
    private String nickname;
}
