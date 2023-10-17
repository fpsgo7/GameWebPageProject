package Park.gamewebpage.service;

import Park.gamewebpage.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 스프링 도메인을 위한 서비스 클래스가아닌
 * 스프링 시큐리티에서 로그인을 진행하기 위해 사용하기 위한
 * 코드이다.
 */
@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {
    private final IUserRepository userRepository;


    /**
     * 사용자 이름으로 정보를 가져오는 메서드로
     * 여기서는 인자값으로 email을 사용할 것이다.
     * @param username the username identifying the user whose data is required.
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException(username));
    }
}
