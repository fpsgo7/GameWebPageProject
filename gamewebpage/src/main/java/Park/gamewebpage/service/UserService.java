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
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 유저 추가 서비스
     */
    public Long createUser(CreateUserDTO createUserDTO){
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
}
