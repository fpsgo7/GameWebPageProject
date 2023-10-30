package Park.gamewebpage.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name ="Characters")// 테이블이름을 UserCharacters 로 설정
@Entity
@Getter
@Setter
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
