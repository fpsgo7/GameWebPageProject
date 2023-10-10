package Park.gamewebpage.controller;

import Park.gamewebpage.domain.FreeBoard;
import Park.gamewebpage.dto.CreateFreeBoardDTO;
import Park.gamewebpage.service.FreeBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class FreeBoardController {
    private final FreeBoardService freeBoardService;

    /**
     * 자유 게시판 글생성 컨트롤러이다.
     * @param createFreeBoardDTO
     * @return HTTP 상태 코드는 생성이며
     * HTTP body에는 FreeBoard 객체로 반환한다.
     */
    public ResponseEntity<FreeBoard> createFreeBoard
            (@RequestBody CreateFreeBoardDTO createFreeBoardDTO){
        FreeBoard freeBoard
                = freeBoardService.createFreeBoard(createFreeBoardDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(freeBoard);
    }
}
