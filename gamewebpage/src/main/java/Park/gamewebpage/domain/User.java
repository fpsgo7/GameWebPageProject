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
public class User implements UserDetails {

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

    // 사용자의 권한들 반환 메서드
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
    // 비밀번호 반환 메서드
    @Override
    public String getPassword() {
        return password;
    }
    // 사용자의 고유값 반환 (로그인으로 사용할 값 )
    @Override
    public String getUsername() {
        return email;
    }
    
    //계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    // 계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    // 패스워드 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    // 계정 사용가능 여부 반환
    @Override
    public boolean isEnabled() {
        return true;
    }
}
