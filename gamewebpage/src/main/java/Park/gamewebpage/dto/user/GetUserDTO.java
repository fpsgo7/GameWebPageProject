package Park.gamewebpage.dto.user;

import Park.gamewebpage.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor // 기본 생성자
public class GetUserDTO {
    private Long id;
    private String email;
    private String password;
    private String nickname;

    public GetUserDTO(User user){
        id = user.getId();
        email = user.getEmail();
        password = user.getPassword();
        nickname = user.getNickname();
    }
}
