package Park.gamewebpage.controller;

import Park.gamewebpage.dto.token.CreateAccessTokenRequest;
import Park.gamewebpage.dto.token.CreateAccessTokenResponse;
import Park.gamewebpage.service.TokenService;
import Park.gamewebpage.url.URL;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 토큰 API 컨트롤러
 */
@RequiredArgsConstructor
@RestController
public class TokenApiController {
    private final TokenService tokenService;

    /**
     * 새로운 액세스 토큰을 생성하는 컨트롤러
     *
     * /api/token POST 요청이 오면
     * 토큰 서비스에서 리프레시 토큰을 기반으로
     * 새로운 액세스 토큰을 만들어주는 메서드
     */
    @PostMapping(URL.TOKEN_API)
    public ResponseEntity<CreateAccessTokenResponse> createNewAccessToken(
            @RequestBody CreateAccessTokenRequest request
            ){
        String newAccessToken = tokenService.createNewAccessToken(
                request.getRefreshToken());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateAccessTokenResponse(newAccessToken));
    }
}
