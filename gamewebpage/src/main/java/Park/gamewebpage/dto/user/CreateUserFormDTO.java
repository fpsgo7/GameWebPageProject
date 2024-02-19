package Park.gamewebpage.dto.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * 유저 생성 폼을 위한
 * 유저 dto이다.
 */
@Getter
@Setter
public class CreateUserFormDTO {
    @Size(min = 3, max = 25)
    @NotEmpty(message = "이메일은 필수항목입니다.")
    @Email
    private String email;

    @Size(min = 3, max = 25)
    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String password;

    @Size(min = 3, max = 25)
    @NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
    private String passwordCheck;

    @Size(min = 3, max = 25)
    @NotEmpty(message = "닉네임은 필수항목입니다.")
    private String nickname;
}
