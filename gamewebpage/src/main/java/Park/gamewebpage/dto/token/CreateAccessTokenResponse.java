package Park.gamewebpage.dto.token;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 액세스 토큰을 응답하기위한 DTO
 */
@AllArgsConstructor
@Getter
public class CreateAccessTokenResponse {
    private String accessToken;
}