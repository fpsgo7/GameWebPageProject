package Park.gamewebpage.config.oauth;

import Park.gamewebpage.domain.User;
import Park.gamewebpage.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * OAuth2를 위한 서비스 클래스
 * DefaultOAuth2UserService 상속받아 만들어진다.
 * 사용자 조회후, users테이블을 업데이트
 * OAuth2의 로그인 정보를 토대로 DB에 유저 정보를 저장한다.
 *
 * SecurityConfig 에서 oauth2Login() 설정 파트에서
 * .userService(oAuth2UserService); 을 통해 쓰인다
 * 그래서 매서드에서는 사용위치가 나오지 않는다.
 *
 * loadUser() : 서버의 users 테이블에서 사용자 정보를 조회한다.
 * saveOrUpdate() : users 테이블에 사용자 정보가 있다면 이름 없데이트
 * 없다면 users 테이블에 회원 데이터 추가
 */
@RequiredArgsConstructor
@Service
public class OAuth2UserService extends DefaultOAuth2UserService {
    private final IUserRepository iUserRepository;

    /**
     * 요청을 바탕트로 유저 정보를 객체로 반환한다.
     * 그리고 유저 업데이트를 한다.
     *
     * 스프링 시큐리티를 통해 자동 호출되며
     * 기존의 OAuth2User정보를 가져옴과 동시에
     * saveOrUpdate(user);로
     * 해당 유저를 업데이트하거나 유저를생성해준다.
     * @param userRequest the user request
     * @return 유저 정보 객체
     * @throws OAuth2AuthenticationException
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // accessToken으로 서드파티에 요청해서 사용자 정보를 얻어온다.
        OAuth2User user = super.loadUser(userRequest);
        saveOrUpdate(user);
        return user;
    }

    /**
     * 해당 유저가 존재하면 업데이트, 없으면 유저생성
     * @param oAuth2User OAuth2User형 유저 객체
     */
    private void saveOrUpdate(OAuth2User oAuth2User) {

        Map<String, Object> oAuth2UserAttributes
                = oAuth2User.getAttributes();
        String email = (String) oAuth2UserAttributes.get("email");
        String name = (String) oAuth2UserAttributes.get("name");
        User user = iUserRepository.findByEmail(email)
                .map((entity -> entity.updateNickName(name)))
                .orElse(User.builder()
                        .email(email)
                        .nickname(name)
                        .build());
        // save 메서드는 CrudRepository의
        // save 메서드로 사용하면 테이블에 저장하는
        // 기능을하며 이미 존재하는 대상이면
        // 업데이트를 하게된다.
        iUserRepository.save(user);
    }
}
