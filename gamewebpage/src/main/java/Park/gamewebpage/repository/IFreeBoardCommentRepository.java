package Park.gamewebpage.repository;

import Park.gamewebpage.domain.FreeBoardComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 댓글 저장소 이다.
 */
public interface IFreeBoardCommentRepository extends JpaRepository<FreeBoardComment, Long> {
    Optional<FreeBoardComment> findByFreeBoardIdAndId(Long freeBoardId, Long id);
}
