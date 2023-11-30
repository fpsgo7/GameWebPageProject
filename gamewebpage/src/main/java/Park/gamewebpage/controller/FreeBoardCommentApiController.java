package Park.gamewebpage.controller;

import Park.gamewebpage.domain.FreeBoard;
import Park.gamewebpage.domain.FreeBoardComment;
import Park.gamewebpage.dto.freeboard.api.CreateFreeBoardDTO;
import Park.gamewebpage.dto.freeboardcomment.api.CreateFreeBoardCommentDTO;
import Park.gamewebpage.service.FreeBoardCommentService;
import Park.gamewebpage.service.FreeBoardService;
import Park.gamewebpage.url.URL;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    (@PathVariable Long id,
            @RequestBody CreateFreeBoardCommentDTO createFreeBoardCommentDTO, Principal principal){
        createFreeBoardCommentDTO.setFreeBoard(freeBoardService.getFreeBoard(id));
        FreeBoardComment freeBoardComment
                = freeBoardCommentService.createFreeBoardComment(createFreeBoardCommentDTO,principal.getName());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(freeBoardComment);
    }
}
