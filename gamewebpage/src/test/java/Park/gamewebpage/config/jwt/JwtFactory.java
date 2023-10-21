package Park.gamewebpage.config.jwt;

import Park.gamewebpage.config.token.jwt.JwtProperties;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.util.*;

/**
 * 토큰 제공자 테스트를 위해
 * 만들어진 모킹(테스트)용
 * 토큰 객체 이다.
 */
@Getter
public class JwtFactory {
    // 기본값들 지정
    private String subject = "test@email.com";// 토큰 제목
    private Date issuedAt = new Date(); // 토큰 발급 시간
    private Date expiration = new Date(
            new Date().getTime()
            + Duration.ofDays(7).toMillis()
    );// 만료시간 7일후

    // 클레임들을 저장할 공간
    private Map<String, Object> claims
            = Collections.emptyMap();

    /**
     * 빌더 패턴으로 설정에 필요한 데이터만
     * 선택하여 기존 값에 적용한다.
     */
    @Builder
    public JwtFactory(String subject, Date issuedAt,
                      Date expiration,
                      Map<String, Object> claims){
        // 매개변수로 온 값이 null 이 아니라면 해당 값을 적용해준다.
        this.subject = subject != null ? subject : this.subject;
        this.issuedAt = issuedAt != null ? issuedAt : this.issuedAt;
        this.expiration = expiration != null ? expiration : this.expiration;
        this.claims = claims != null ? claims : this.claims;
    }

    /**
     * jjwt 라이브러리를 사용해 JWT 토큰 생성
     * 해당 클래스의 변수들에 저장된 값을 활용하여
     * 토큰을 생성한다.
     */
    public String createToken(JwtProperties jwtProperties){
        return Jwts.builder()
                .setSubject(subject)
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS256,
                        jwtProperties.getSecretKey())
                .compact();
    }
}
