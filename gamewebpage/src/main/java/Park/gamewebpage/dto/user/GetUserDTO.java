package Park.gamewebpage.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor// 모든 변수에대한 매개변수가 있는 생성자
public class GetUserDTO {
    private Long id;
    private String email;
    private String password;
    private String nickname;
}
