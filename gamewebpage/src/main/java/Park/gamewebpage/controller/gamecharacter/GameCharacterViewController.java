package Park.gamewebpage.controller.gamecharacter;

import Park.gamewebpage.util.AddAttributeForModel;
import Park.gamewebpage.domain.GameCharacter;
import Park.gamewebpage.dto.Character.GetGameCharacterDTO;
import Park.gamewebpage.service.GameCharacterService;
import Park.gamewebpage.service.UserService;
import Park.gamewebpage.url.URL;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;


@RequiredArgsConstructor
@Controller
public class GameCharacterViewController {
    private  final GameCharacterService gameCharacterService;
    private final UserService userService;

    /**
     * 게임 캐릭터를 조회한다
     * 만약 게임캐릭터가 없다면 null을 반환시킨다.
     * @return 게임 캐릭터 객체 또는 null
     */
    @GetMapping(URL.GAME_CHARACTER_VIEW)
    public String getGameCharacterView(
            Principal principal, Model model
    ){
        GameCharacter gameCharacter = gameCharacterService.findByEmail(principal.getName());

        if(gameCharacter != null){
            model.addAttribute("gameCharacter"
                    , new GetGameCharacterDTO(gameCharacter));
        }else {
            model.addAttribute("gameCharacter"
                    , new GetGameCharacterDTO(null,0L,null));
        }

        AddAttributeForModel.getUserInfo(userService,principal,model);
        return "gameCharacter/gameCharacter";
    }
}
