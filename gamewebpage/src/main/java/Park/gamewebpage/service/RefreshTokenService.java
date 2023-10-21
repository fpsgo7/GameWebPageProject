package Park.gamewebpage.service;

import Park.gamewebpage.domain.RefreshToken;
import Park.gamewebpage.repository.IRefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 리프레시 토큰 서비스
 */
@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final IRefreshTokenRepository refreshTokenRepository;

    /**
     * 리프레시 토큰을 통해 검색해서
     * 리프레시 토큰 객체를 반환하는 메서드
     */
    public final RefreshToken findByRefreshToken(String refreshToken){
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(()-> new IllegalArgumentException("Unexpected token"));
    }
}
