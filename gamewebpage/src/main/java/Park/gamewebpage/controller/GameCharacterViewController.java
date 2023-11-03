package Park.gamewebpage.controller;

import Park.gamewebpage.domain.GameCharacter;
import Park.gamewebpage.dto.Character.GetGameCharacterDTO;
import Park.gamewebpage.service.GameCharacterService;
import Park.gamewebpage.url.URL;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RequiredArgsConstructor
@Controller
public class GameCharacterViewController {
    private  final GameCharacterService gameCharacterService;

    /**
     * 게임 캐릭터를 조회한다
     * 만약 게임캐릭터가 없다면 null을 반환시킨다.
     * @param email
     * @return 게임 캐릭터 객체 또는 null
     */
    @GetMapping(URL.GAME_CHARACTER_VIEW_BY_EMAIL)
    public String getGameCharacterView(
            @PathVariable String email, Model model
    ){
        GameCharacter gameCharacter = gameCharacterService.findByEmail(email);

        if(gameCharacter != null){
            model.addAttribute("gameCharacter"
                    , new GetGameCharacterDTO(gameCharacter));
        }else {
            model.addAttribute("gameCharacter"
                    , new GetGameCharacterDTO(null,0,null));
        }
        return "gameCharacter/gameCharacter";
    }
}
