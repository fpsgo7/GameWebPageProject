package Park.gamewebpage.controller;

import Park.gamewebpage.dto.freeboardDTO.view.GetFreeBoardViewDTO;
import Park.gamewebpage.service.FreeBoardService;
import Park.gamewebpage.url.URL;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class FreeBoardViewController {

    private final FreeBoardService FREE_BOARD_SERVICE;

    @GetMapping(URL.FREE_BOARD_VIEW_URL)
    public String getFreeBoardListView(Model model){
        List<GetFreeBoardViewDTO> freeBoardList
                = FREE_BOARD_SERVICE.getListFreeBoard()
                .stream()
                .map(GetFreeBoardViewDTO::new)
                .collect(Collectors.toList());

        model.addAttribute("FreeBoardList",freeBoardList);

        return "FreeBoardList";

    }

    @GetMapping(URL.CREATE_FREE_BOARD_VIEW_URL)
    public String newFreeBoardView(Model model){
        return "createFreeBoard";
    }
}
