package Park.gamewebpage.config.oauth;

import Park.gamewebpage.domain.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class CustomUserDetails implements UserDetails , OAuth2User {

    private User user;
    private Map<String, Object> attributes = null;

    // 일반 시큐리티 로그인시 사용
    public CustomUserDetails(User user) {
        this.user = user;
    }

    // OAuth2.0 로그인시 사용
    public CustomUserDetails(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    /* Oauth2 유저 관련 메서드*/
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return null;
    }

    /* UserDetils관련 메서드 */
    // 사용자의 권한들 반환 메서드
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
    // 비밀번호 반환 메서드
    @Override
    public String getPassword() {
        return user.getPassword();
    }
    // 사용자의 고유값 반환 (로그인으로 사용할 값 )
    @Override
    public String getUsername() {
        return user.getEmail();
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
