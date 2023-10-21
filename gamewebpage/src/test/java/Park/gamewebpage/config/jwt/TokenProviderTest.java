package Park.gamewebpage.config.jwt;

import Park.gamewebpage.domain.User;
import Park.gamewebpage.repository.IUserRepository;
import io.jsonwebtoken.Jwts;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Duration;
import java.util.Date;
import java.util.Map;


@SpringBootTest
class TokenProviderTest {

    @Autowired
    private TokenProvider tokenProvider;// 토큰 제공자
    @Autowired
    private IUserRepository userRepository;// 유저 저장소
    @Autowired
    private JwtProperties jwtProperties;// 이슈 발급자와 비밀키값

    /**
     * 토큰을 생성하여 잘만들어졌는지 확인한다.
     */
    @DisplayName("generateToken(): 유저 정보와 만료기간을 전달해 토큰을 만들었습니다.")
    @Test
    void generateToken() {
        /**
         * given
         * 토큰에 유저 정보를 추가하기 위한 테스트 유저를 만든다.
         */
        User testUser = userRepository.save(User.builder()
                .email("Park@email.com")
                .build());
        /**
         * when
         * 토큰 제공자의 generateToken() 메서드를 호출해 토큰을 만든다.
         */
        String token = tokenProvider.generateToken(testUser, Duration.ofDays(14));
        /**
         * then
         * jjwt라이브러리를 사용해 토큰을 복호화한다.
         * 토큰을 만들 때 클레임으로 넣어둔 id 값이
         * given 절에서 만든 유저 ID와 동일한지 확인한다.
         */
        Long userId = Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .get("id", Long.class);

        Assertions.assertThat(userId).isEqualTo(testUser.getId());
    }

    /**
     * 만료된 토큰인 경우 유효성 검증에 실패한다.
     */
    @DisplayName("validToken_failed(): 만료된 토큰인 경우 유효성 검증에 실패한다.")
    @Test
    void validToken_failed() {
        /**
         * given
         * jjwt 라이브러리를 사용해 토큰을 생성한다.
         * 만료시간은 1970년 1월 1일 부터 현재 시간을 밀리초 단위로 치환한 값(new Date()
         * .getTime())에 7일치 값을 빼 , 이미 만료된 토큰으로 생성합니다
         */
        String token = JwtFactory.builder()
                .expiration(
                        new Date(new Date().getTime()
                        - Duration.ofDays(7).toMillis())
                ).build()
                .createToken(jwtProperties);
        /**
         * when
         * 토큰 제공자의 validToken() 메서드를 호출해 유효한
         * 토큰인지 검증한 뒤 결과값을 반환받습니다.
         */
        boolean result = tokenProvider.validToken(token);
        /**
         * then
         * 반환값이 false(유효한 토큰이 아님) 인 것을 확인합니다.
         */
        Assertions.assertThat(result).isFalse();
    }

    /**
     * 만료되지 않은 토큰인 경우 검증 성공한다.
     */
    @DisplayName("validToken_success(): 유효한 토큰인 경우 유효성 검증에 성공한다")
    @Test
    void validToken_success(){
        /**
         * given
         * jjwt 라이브러리를 사용해 토큰을 생성한다.
         * 만료시간은 현재 시간으로부터 7일 뒤로 ,
         * 만료되지 않은 토큰으로 생성한다.
         */
        String token = JwtFactory
                .builder().build().createToken(jwtProperties);
        /**
         * when
         * 토큰 제공자의 validToken() 메서드를 호출해
         * 유효한 토큰인지 검증한 뒤 결과값을 반환받습니다.
         */
        boolean result = tokenProvider.validToken(token);
        /**
         * then
         * 반환값이 true(유효한 토큰임) 인 것을 확인합니다.
         */
        Assertions.assertThat(result).isTrue();
    }

    /**
     * 토큰을 전달 받아 인증 정보를 담은 객체
     * Authentication을 반환하는 메서드인
     * getAuthentication()을 테스트한다.
     */
    @DisplayName("getAuthentication() : 토큰 기반으로 인증 정보를 가져올 수 있다.")
    @Test
    void getAuthentication() {
        /**
         * given
         * jjwt 라이브러리를 사용해 토큰을 생성한다.
         * 토큰의 제목인 subject는
         * “user@email.com”라는 값을 사용한다.
         */
        String userEmail = "user@email.comm";
        String token = JwtFactory.builder()
                .subject(userEmail)
                .build()
                .createToken(jwtProperties);
        /**
         * when
         * 토큰 제공자의 getAuthentication()
         * 메서드를 호출해 인증 객체를 반환 받습니다.
         */
        Authentication authentication = tokenProvider.getAuthentication(token);
        /**
         * then
         * 반환받은 인증 객체의 유저 이름을 가져와
         * given 절에서 설정한 subject 값인
         * “user@email.com” 과 같은지 확인합니다.
         */
        Assertions.assertThat(
                // 현재 사용자 정보를 가져와 유저이름 즉 이메일 구하기
                ((UserDetails)authentication.getPrincipal()).getUsername()
        ).isEqualTo(userEmail);
    }

    /**
     * 토큰으로 유저 ID를 가져올 수 있는지 테스트
     */
    @DisplayName("getUserId(): 토큰으로 유저 ID를 가져올 수 있다.")
    @Test
    void getUserId() {
        /**
         * given
         * jjwt 라이브러리를 사용해 토큰을 생성한다.
         * 이 때 클래임을 추가하여.
         * 키는 “id” , 값은 1이라는 유저 ID입니다.
         */
        Long userId = 1L;
        String token = JwtFactory.builder()
                .claims(Map.of("id",userId))
                .build()
                .createToken(jwtProperties);
        /**
         * when
         * 토큰 제공자의 getUserId()
         * 메서드를 호출해 유저 ID를 반환받습니다.
         */
        Long userIdByToken = tokenProvider.getUserId(token);

        /**
         * then
         * 반환받은 유저 ID가
         * given 절에서 설정한 유저 ID 값인 1과 같은지 확인한다.
         */
        Assertions.assertThat(userIdByToken).isEqualTo(userId);
    }
}