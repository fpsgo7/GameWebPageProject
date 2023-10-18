package Park.gamewebpage.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Table(name ="users")// 테이블이름을 users로 설정
@Entity
@NoArgsConstructor
@Getter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    public Long id;

    // 실질적으로 로그인에 사용되는 아이디
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Builder
    public User(String email,String password){
        this.email = email;
        this.password = password;
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
