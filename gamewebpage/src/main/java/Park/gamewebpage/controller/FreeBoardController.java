package Park.gamewebpage.controller;

import Park.gamewebpage.service.FreeBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class FreeBoardController {
    private final FreeBoardService freeBoardService;


}
