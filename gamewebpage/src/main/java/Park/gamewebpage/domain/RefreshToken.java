package Park.gamewebpage.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 리프레시 토큰 엔티티
 */
@Table(name ="refresh_token")
@NoArgsConstructor(access = AccessLevel.PROTECTED)// 기본 생성자를 protected로 설정
@Getter
@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    /**
     * 리프레시 토큰 생성자
     * @param userId 유저 아이디
     * @param refreshToken jwt 토큰값
     */
    public RefreshToken(Long userId, String refreshToken){
        this.userId = userId;
        this.refreshToken = refreshToken;
    }

    /**
     * 리프레시 토큰 업데이트
     * @param newRefreshToken 새로운 토큰값
     * @return 해당 토큰 반환
     */
    public RefreshToken updateRefreshToken(String newRefreshToken){
        this.refreshToken = newRefreshToken;
        return this;
    }
}
