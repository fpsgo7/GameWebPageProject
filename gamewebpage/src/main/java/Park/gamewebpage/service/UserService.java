package Park.gamewebpage.service;

import Park.gamewebpage.domain.User;
import Park.gamewebpage.dto.user.CreateUserDTO;
import Park.gamewebpage.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final IUserRepository userRepository;

    /**
     * 유저 추가 서비스
     */
    public Long createUser(CreateUserDTO createUserDTO){
        /**
         * 해당 객체를 클래스 자체에서 생성하면 SecrityConfig와의
         * 스프링 빈순환 참조가 발생할 수 있으므로
         * 메서드에 작성해준다.
         */
        BCryptPasswordEncoder bCryptPasswordEncoder
                = new BCryptPasswordEncoder();

        return userRepository
                .save(User
                        .builder()
                        .email(createUserDTO.getEmail())
                        .password(bCryptPasswordEncoder
                                .encode(createUserDTO.getPassword()))
                        .nickname(createUserDTO.getNickname())
                        .build())
                .getId(); // 아이디 반환
    }

    /**
     * 유저 ID로 유저를 검색해서 전달하는 메서드
     */
    public User findById(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(()-> new IllegalArgumentException("Unexpected user"));
    }

    /**
     * 이메일을 입력받아 users 테이블에서
     * 유저를 찾고 없으면
     * 예외를 발생시킨다.
     * OAuth2에서 제공하는 이메일은 유일 값이기 때문에 해당 메서드로 유저를 찾을 수 있기 때문이다.
     */
    public  User findByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    /**
     * 회원 탈퇴 서비스
     */
    public void delete(User user){
        userRepository.delete(user);
    }
}
