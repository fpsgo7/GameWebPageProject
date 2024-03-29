package Park.gamewebpage.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name ="Game_High_Scores")// 테이블이름을 GameCharacters 로 설정
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)// 기본 생성자 접근제한
@AllArgsConstructor
public class GameHighScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    public Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "game_character_nickname")
    private String gameCharacterNickname;

    @Column(name = "high_score", nullable = false)
    private Long highScore;

    @Column(name = "lasted_time", nullable = false)
    private LocalDateTime lastedTime;

    @Builder
    public GameHighScore(String email, String gameCharacterNickname, Long highScore, LocalDateTime lastedTime) {
        this.email = email;
        this.gameCharacterNickname = gameCharacterNickname;
        this.highScore = highScore;
        this.lastedTime = lastedTime;
    }
}
