package Park.gamewebpage.domain;

import lombok.*;

import javax.persistence.*;

@Table(name ="GameCharacters")// 테이블이름을 UserCharacters 로 설정
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)// 기본 생성자
public class GameCharacter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    public Long id;

    // users 테이블의 이메일 참조
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "nickname", unique = false)
    private String nickname;
    
    @Column(name = "high_score", unique = false)
    private int highScore;

    @Builder
    public GameCharacter(String email, String nickname, int highScore){
        this.email = email;
        this.nickname = nickname;
        this.highScore = highScore;
    }
}
