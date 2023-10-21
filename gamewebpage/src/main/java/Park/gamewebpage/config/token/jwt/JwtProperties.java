package Park.gamewebpage.config.token.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * jwt의 속성을 위한 클래스
 * application.properties 에 jwt
 * 라는 이름으로 설정된
 * 이슈 발급자와 비밀키값에 접근하여
 * 값을 가져온다.
 * @ConfigurationProperties("jwt")
 * 덕분에 간단하게 가능하다.
 */
@Getter
@Setter
@Component
// 자바 클래스에 프로퍼티 값을 가져와서 사용하는 애너테이션
@ConfigurationProperties("jwt")
public class JwtProperties {
    private String issuer;// 이슈 발급자 값
    private String secretKey; // 비밀키 값
}
