package Park.gamewebpage.dto.freeboardDTO.user;

import lombok.Getter;
import lombok.Setter;

/**
 * 유저 생성을 위한 DTO 파일
 * 이메일 과
 * 비밀번호 정보만을 사용한다.
 */
@Getter
@Setter
public class CreateUserDTO {
    private String email;
    private String password;
}
