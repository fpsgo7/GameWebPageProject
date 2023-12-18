package Park.gamewebpage.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 비밀번호값을 현재 값과 바꿀값 2개를 가지는
 * DTO이다.
 */
@Getter
@Setter
@NoArgsConstructor // 기본 생성자
public class UpdatePWDTO {
    String newPassword;
    String oldPassword;
}
