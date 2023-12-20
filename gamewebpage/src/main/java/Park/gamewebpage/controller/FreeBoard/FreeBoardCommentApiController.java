package Park.gamewebpage.controller.FreeBoard;

import Park.gamewebpage.domain.FreeBoardComment;
import Park.gamewebpage.dto.freeboardcomment.CreateFreeBoardCommentDTO;
import Park.gamewebpage.dto.freeboardcomment.UpdateFreeBoardCommentDTO;
import Park.gamewebpage.service.FreeBoardCommentService;
import Park.gamewebpage.service.FreeBoardService;
import Park.gamewebpage.url.URL;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
public class FreeBoardCommentApiController {

    private final FreeBoardCommentService freeBoardCommentService;
    private final FreeBoardService freeBoardService;

    /**
     * 자유 게시판의 댓글생성 컨트롤러이다.
     * @param createFreeBoardCommentDTO
     * @return HTTP 상태 코드는 생성이며
     * HTTP body에는 FreeBoard 객체로 반환한다.
     */
    @PostMapping(URL.FREE_BOARD_COMMENT_API)
    public ResponseEntity<FreeBoardComment> createFreeBoardComment
    // Principal 로그인된 유저의 앤티티 를 나타내는 인터페이스이다.
    (@PathVariable Long freeBoardId,
            @RequestBody CreateFreeBoardCommentDTO createFreeBoardCommentDTO, Principal principal){
        createFreeBoardCommentDTO.setFreeBoard(freeBoardService.getFreeBoard(freeBoardId));
        FreeBoardComment freeBoardComment
                = freeBoardCommentService.createFreeBoardComment(createFreeBoardCommentDTO,principal.getName());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(freeBoardComment);
    }

    /**
     * 자유 게시판의 댓글 수정 컨트롤러이다.
     * @param freeBoardId : 자유 게시판 아이디
     * @param id : 댓글 아이디
     * @param updateFreeBoardCommentDTO : 댓글 객체
     * @return 업데이트를 해준다.
     */
    @PatchMapping(URL.FREE_BOARD_COMMENT_API_BY_ID)
    public ResponseEntity<Long> update
            (@PathVariable Long freeBoardId,
             @PathVariable Long id,
             @RequestBody UpdateFreeBoardCommentDTO updateFreeBoardCommentDTO) {
        freeBoardCommentService.update(freeBoardId, id, updateFreeBoardCommentDTO);
        return ResponseEntity.ok(id);
    }

    /**
     * 자유 게시판의 댓글을 삭제한다.
     * @param freeBoardId 자유게시판 글 아이디
     * @param id 아이디
     * @return
     */
    @DeleteMapping(URL.FREE_BOARD_COMMENT_API_BY_ID)
    public ResponseEntity<Long> delete
            (@PathVariable Long freeBoardId,
             @PathVariable Long id) {
        freeBoardCommentService.delete(freeBoardId, id);
        return ResponseEntity.ok(id);
    }
}
