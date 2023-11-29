package Park.gamewebpage.service;

import Park.gamewebpage.domain.FreeBoardComment;
import Park.gamewebpage.dto.freeboardcomment.api.CreateFreeBoardCommentDTO;
import Park.gamewebpage.repository.IFreeBoardCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FreeBoardCommentService {

    private final IFreeBoardCommentRepository iFreeBoardCommentRepository;

    /**
     * 자유 게시판 댓글 객체가 생성된다.
     * CreateFreeBoardDTO 를 받아 엔티티 객체화 시킨후
     * FreeBoard 객체화 된 객체를 반환한다.
     * @param createFreeBoardCommentDTO 댓글 객체를 생성하기 위한  DTO
     * @return FreeBoardComment 저장소에 저장되는 객체가 반환된다.
     */
    public FreeBoardComment createFreeBoardComment(CreateFreeBoardCommentDTO createFreeBoardCommentDTO, String userName){
        return iFreeBoardCommentRepository
                .save(createFreeBoardCommentDTO.toEntity(userName));
    }

    /**
     * 해당 게시판 글의 댓글을 가져오는 것은
     * 자유 게시판 서비스에서 담당한다.
     */


}
