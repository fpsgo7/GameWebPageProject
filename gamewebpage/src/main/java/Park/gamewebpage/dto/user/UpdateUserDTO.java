package Park.gamewebpage.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor // 기본 생성자
public class UpdateUserDTO {
    String password = null;
    String nickname = null;
}
