package Park.gamewebpage.controller;

import Park.gamewebpage.domain.FreeBoard;
import Park.gamewebpage.dto.freeboard.view.GetFreeBoardViewDTO;
import Park.gamewebpage.dto.freeboard.view.GetFreeBoardViewListDTO;
import Park.gamewebpage.dto.freeboard.view.UpdateFreeBoardViewDTO;
import Park.gamewebpage.service.FreeBoardService;
import Park.gamewebpage.url.URL;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class FreeBoardViewController {

    private final FreeBoardService freeBoardService;

    /**
     * 자유 게시판 글 리스트  뷰 컨트롤러
     * @param model
     * @return
     */
    @GetMapping(URL.FREE_BOARD_VIEW)
    public String getFreeBoardListView(Model model){
        List<GetFreeBoardViewListDTO> freeBoardList
                = freeBoardService.getListFreeBoard()
                .stream()
                .map(GetFreeBoardViewListDTO::new)
                .collect(Collectors.toList());

        model.addAttribute("FreeBoardList",freeBoardList);

        return "freeBoard/freeBoardList";

    }

    /**
     * 자유 게시판 글 뷰
     * @param id
     * @param model
     * @return
     */
    @GetMapping(URL.FREE_BOARD_VIEW_BY_ID)
    public String getFreeBoardView(
            @PathVariable Long id, Model model
    ){
        FreeBoard freeBoard = freeBoardService.getFreeBoard(id);
        model.addAttribute("freeBoard",
                new GetFreeBoardViewDTO(freeBoard));
        return "freeBoard/freeBoard";
    }

    /**
     * 자유게시판 글 생성 뷰 컨트롤러
     * @param model
     * @return
     */
    @GetMapping(URL.CREATE_FREE_BOARD_VIEW)
    public String createFreeBoardView(Model model){
        return "freeBoard/createFreeBoard";
    }

    /**
     * 자유 게시판 수정 화면 뷰 컨트롤러
     * @param id
     * @param model
     * @return
     */
    @GetMapping(URL.UPDATE_FREE_BOARD_VIEW)
    public String newFreeBoardView(@RequestParam Long id, Model model){
        FreeBoard freeBoard = freeBoardService.getFreeBoard(id);
        model.addAttribute("freeBoard",new UpdateFreeBoardViewDTO(freeBoard));
        return "freeBoard/updateFreeBoard";
    }
}
