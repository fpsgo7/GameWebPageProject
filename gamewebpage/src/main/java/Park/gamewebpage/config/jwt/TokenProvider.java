package Park.gamewebpage.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import Park.gamewebpage.domain.User;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class TokenProvider {
    // jwt 속성 접근을 위한 변수
    private final JwtProperties jwtProperties;

    public String generateToken(User user, Duration expiryTime){
        Date now = new Date();
        return  makeToken(user, new Date((now.getTime() + expiryTime.toMillis())));
    }

    /**
     * 토큰을 생성하는 메서드이다.
     * 인자값으로 유저정보 user과 만료시간 expiryTime을 받는다.
     * set 계열의 메서드로 여러값을 지정하며
     * 헤더는 typ(타입)
     * 등록된 클레임 내용은 iss(발급자), iat(발급 일시), exp(만료 일시) sug(토큰제목) 이
     * 비공개 클레임 에는 유저 ID를 지정한다.
     * 토큰을 만들 때는 application.properties파일에 선언해둔
     * 비밀값과 함꼐 HS256 방식으로 암호화 한다.
     */
    private String makeToken(User user,Date expiryTime){
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)// 헤더 타입 : JWT
                // 등록된 클레임들
                .setIssuer(jwtProperties.getIssuer())// JWT의 issuer값 : Park@email.com
                .setIssuedAt(now)// 현제시간 : now
                .setExpiration(expiryTime)// 만료시간 : expiryTime
                .setSubject(user.getEmail())// 토큰제목 : 유저의 이메일
                // 그외 내용 클래임
                .claim("id", user.getId())// 클래임 id : 유저 id
                // 서명 파트 : properties에서 지정한  비밀값과 함께 HS256 방식으로 암호화한다.
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }
    /**
     * 토큰이 유효한지 검증해주는 메서드
     * - application.properties 파일에 선언한 비밀값과 함꼐 토큰 복호화(암호문 → 평문) 진행한다.
     * - 복호화가 실패한다면 flase를 반환한다.
     */
    public boolean validToken(String token){
        try{
            Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey())// 비밀값으로 복호화
                    .parseClaimsJws(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 토큰을 받아 인증정보를 담은 객체 Authentication을 반환하는 메서드
     * - application.properties파일에 저장한 비밀값으로 토큰을 복호화한 뒤
     * 클레임을 가져오는 private 메서드인 getClaims() 를 호출해서 클레임 정보를 반환받아
     * 사용자 이메일이 들어 있는 토큰 제목 sub와 토큰 기반으로 인증 정보를 생성한다.
     * - 여기서 사용되는 UsernamePasswordAuthenticationToken 은
     * 우리가 만든 클래스가 아닌 스프링 시큐리티에서 제공하는 클래스이다.
     */
    public Authentication getAuthentication(String token){
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authorities = Collections
                .singleton(new SimpleGrantedAuthority("ROLE_USER"));
        return new UsernamePasswordAuthenticationToken(
                new org.springframework.security.core.userdetails
                        .User(claims.getSubject(),"",authorities),token,authorities);
    }

    /**
     * 인자값으로 온 해당 토큰의
     * 클레임들을 조회하기 위한 메서드이다.
     */
    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 토큰 기반으로 사용자 ID를 가져오는 메서드이다.
     *
     * - application.properties 파일에 저장한 비밀값으로 토큰을 복호화한 다음
     * - 클레임을 가져오는 private 메서드인 getClaims()를 호출해서 클레임 정보를 반환받고
     * - 클레임에서 id키로 저장된 값을 가져와 반환해준다.
     */
    public Long getUserId(String token){
        Claims claims = getClaims(token);
        return claims.get("id", Long.class);
    }
}
