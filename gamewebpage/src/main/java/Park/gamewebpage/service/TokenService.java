package Park.gamewebpage.service;

import Park.gamewebpage.config.token.jwt.TokenProvider;
import Park.gamewebpage.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * 토큰 서비스
 */
@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    /**
     * 리프레시 토큰을 전달받아 유효성 검사후
     * 유효한 경우 해당 토큰으로 사용자 ID를 찾아
     * 토큰 제공자의 generateToken() 을 호출하여
     * 새로운 액세스 토큰을 생성 반환한다.
     */
    public String createNewAccessToken(String refreshToken){
        // 토큰이 유효하지 않다면 예외 발생
        if(!tokenProvider.validToken(refreshToken)){
            throw  new IllegalArgumentException("Unexpected token");
        }

        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        User user = userService.findById(userId);
        return tokenProvider.generateToken(user, Duration.ofHours(2));
    }
}
