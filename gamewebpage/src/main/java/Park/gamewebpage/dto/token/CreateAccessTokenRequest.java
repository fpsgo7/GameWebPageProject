package Park.gamewebpage.dto.token;

import lombok.Getter;
import lombok.Setter;

/**
 * 토큰 생성 요청을 위한 DTO
 */
@Getter
@Setter
public class CreateAccessTokenRequest {
    private String refreshToken;
}