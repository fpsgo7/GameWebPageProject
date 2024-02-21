package Park.gamewebpage.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Table(name ="users")// 테이블이름을 users로 설정
@Entity
@NoArgsConstructor
@Getter
@Setter// 유저 정보 수정 작업을 위해 사용한다.
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    // 실질적으로 로그인에 사용되는 아이디
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    // 사용자 이름
    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Builder
    public User(String email,String password,String nickname){
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    /**
     * 닉네임을 변경한다. (중복 허용)
     * @param nickname 닉네임값
     * @return User 객체 반환
     */
    public User updateNickName(String nickname){
        this.nickname = nickname;
        return this;
    }
}
