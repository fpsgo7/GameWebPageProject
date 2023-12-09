package Park.gamewebpage.service;

import Park.gamewebpage.domain.FreeBoardComment;
import Park.gamewebpage.dto.freeboardcomment.api.CreateFreeBoardCommentDTO;
import Park.gamewebpage.dto.freeboardcomment.api.UpdateFreeBoardCommentDTO;
import Park.gamewebpage.repository.IFreeBoardCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     * 자유 게시판 댓글 수정
     * @param freeBoardId 자유게시판 글 아이디 (어느 글 소속인지 알아야함)
     * @param id 댓글 아이디
     * @param updateFreeBoardCommentDTO 가져온 객체
     */
    @Transactional
    public void update(Long freeBoardId, Long id, UpdateFreeBoardCommentDTO updateFreeBoardCommentDTO){
        FreeBoardComment freeBoardComment = iFreeBoardCommentRepository.findByFreeBoardIdAndId(freeBoardId,id)
                .orElseThrow(()->new IllegalArgumentException("해당 댓글은 없습니다."+id));
        freeBoardComment.setComment(updateFreeBoardCommentDTO.getComment());
    }


    @Transactional
    public void delete(Long freeBoardId, Long id){
        FreeBoardComment freeBoardComment = iFreeBoardCommentRepository.findByFreeBoardIdAndId(freeBoardId,id)
                .orElseThrow(()->new IllegalArgumentException("해당 댓글은 없습니다."+id));
        iFreeBoardCommentRepository.delete(freeBoardComment);
    }
}
