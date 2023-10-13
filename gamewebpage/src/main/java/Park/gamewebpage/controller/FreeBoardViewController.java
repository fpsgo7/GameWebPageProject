package Park.gamewebpage.controller;

import Park.gamewebpage.domain.FreeBoard;
import Park.gamewebpage.dto.freeboardDTO.view.GetFreeBoardViewDTO;
import Park.gamewebpage.dto.freeboardDTO.view.UpdateFreeBoardViewDTO;
import Park.gamewebpage.service.FreeBoardService;
import Park.gamewebpage.url.URL;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    @GetMapping(URL.FREE_BOARD_VIEW_URL)
    public String getFreeBoardListView(Model model){
        List<GetFreeBoardViewDTO> freeBoardList
                = freeBoardService.getListFreeBoard()
                .stream()
                .map(GetFreeBoardViewDTO::new)
                .collect(Collectors.toList());

        model.addAttribute("FreeBoardList",freeBoardList);

        return "FreeBoardList";

    }

    /**
     * 자유게시판 글 생성 컨트롤러
     * @param model
     * @return
     */
    @GetMapping(URL.CREATE_FREE_BOARD_VIEW_URL)
    public String createFreeBoardView(Model model){
        return "createFreeBoard";
    }

    /**
     * 자유 게시판 수정 화면 뷰 컨트롤러
     * @param id
     * @param model
     * @return
     */
    @GetMapping(URL.UPDATE_FREE_BOARD_VIEW_URL_BY_ID)
    public String newFreeBoardView(@RequestParam Long id, Model model){
        FreeBoard freeBoard = freeBoardService.getFreeBoard(id);
        model.addAttribute("freeBoard",new UpdateFreeBoardViewDTO(freeBoard));
        return "updateFreeBoard";
    }
}
