package Park.gamewebpage.controller;

import Park.gamewebpage.domain.FreeBoard;
import Park.gamewebpage.dto.CreateFreeBoardDTO;
import Park.gamewebpage.dto.GetFreeBoardDTO;
import Park.gamewebpage.service.FreeBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    @PostMapping("/freeBoard")
    public ResponseEntity<FreeBoard> createFreeBoard
            (@RequestBody CreateFreeBoardDTO createFreeBoardDTO){
        FreeBoard freeBoard
                = freeBoardService.createFreeBoard(createFreeBoardDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(freeBoard);
    }

    /**
     * 자유게시판 글 리스트를
     * 반환한다.
     * @return HTTP 상태코드는 ok이며
     * HTTP body에는 FreeBoard객체들의
     * 리스트를 반환한다.
     */
    @GetMapping("/freeBoard")
    public ResponseEntity<List<GetFreeBoardDTO>> getListFreeBoard(){
        // freeBoardDTOList 에 FreeBoard 객체를 바로
        // 넣지않고 GetFreeBoardDTO DTO 객체로
        // 형변환 한후 대입한다.
        List<GetFreeBoardDTO> freeBoardDTOList
                = freeBoardService.getListFreeBoard()
                .stream()
                //.map(freeBoard -> new GetFreeBoardDTO(freeBoard)) // 해당 문장을 줄이면 밑에처럼된다.
                .map(GetFreeBoardDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(freeBoardDTOList);
    }

    /**
     * 자유 게시판 글조회
     * 메서드
     * @param id
     * @return id 값이 일치하는 GetFreeBoardDTO 객체를
     * 가지고있는 ResponseEntity  
     */
    @GetMapping("/freeBoard/{id}")
    public  ResponseEntity<GetFreeBoardDTO> getFreeBoard(
            @PathVariable long id
    ){
        FreeBoard freeBoard = freeBoardService.getFreeBoard(id);

        return ResponseEntity
                .ok()
                .body(new GetFreeBoardDTO(freeBoard));
    }

    /**
     * 자유 게시판 글 삭제 메서드
     * @param id
     * @return 성공했다고 알려주는 응답코드를 
     * 가지고 있는 ResponseEntity
     */
    @DeleteMapping("/freeBoard/{id}")
    public  ResponseEntity<Void> deleteFreeBoard(
            @PathVariable long id
    ){
        freeBoardService.deleteFreeBoard(id);
        return ResponseEntity
                .ok()
                .build();
    }
}
