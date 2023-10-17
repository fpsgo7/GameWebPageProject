package Park.gamewebpage.repository;

import Park.gamewebpage.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User,Long> {
    // 이메일로 사용자 정보를 가져온다.
    Optional<User> findByEmail(String email);
}
