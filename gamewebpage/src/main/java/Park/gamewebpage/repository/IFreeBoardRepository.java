package Park.gamewebpage.repository;

import Park.gamewebpage.domain.FreeBoard;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * FreeBoard엔티티 객체를 위한 저장소
 * JpaRepository를 상속받음으로써
 * 기본적인 CRUD 작업을 구현할 필요가 없다.
 */
public interface IFreeBoardRepository extends JpaRepository<FreeBoard, Long> {
}
