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

}
