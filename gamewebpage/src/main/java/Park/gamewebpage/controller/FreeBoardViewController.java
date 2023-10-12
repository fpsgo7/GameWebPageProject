package Park.gamewebpage.controller;

import Park.gamewebpage.service.FreeBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class FreeBoardViewController {

    private final FreeBoardService FREE_BOARD_SERVICE;

    @GetMapping("/freeBoards")
    public String getFreeBoardListView(){

        return null;
    }
}
