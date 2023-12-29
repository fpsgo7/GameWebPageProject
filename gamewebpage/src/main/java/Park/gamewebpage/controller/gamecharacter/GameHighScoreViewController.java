package Park.gamewebpage.controller.gamecharacter;

import Park.gamewebpage.domain.GameHighScore;
import Park.gamewebpage.service.GameHighScoreService;
import Park.gamewebpage.url.URL;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class GameHighScoreViewController {
    private  final GameHighScoreService gameHighScoreService;

    /**
     * 게임 캐릭터 랭크를 보여주는 컨트롤러
     * 페이징을 사용한다.
     * @param model
     * @return
     */
    @GetMapping(URL.GAME_CHARACTER_RANK_VIEW)
    public String getRankListGameCharacter(
            Model model,
            // PageRequest.of 를 사용하는 경우 추가해줘야하는 문장잉다.
            @RequestParam(required = false, defaultValue = "0") int page
    ){
        // 정렬 객체 생성 (해당 객체를 통해 정렬된다)
        Sort sort = Sort.by(
                Sort.Order.desc("highScore"),
                Sort.Order.asc("lastedTime")    );
        // Pageable 객체 생성 해당 객체를 통해 Pageable이 적용된다.
        Pageable pageable =
                PageRequest.of(page , 10, sort);
        // 게임캐릭터 순위 리스트 정보 가져오기
        Page<GameHighScore> gameCharacterRankPage
                = gameHighScoreService.getGameCharacterRankPageable(pageable);

        //페이지블럭 처리
        //1을 더해주는 이유는 pageable은 0부터라 1을 처리하려면 1을 더해서 시작해주어야 한다.
        int nowPage = gameCharacterRankPage.getPageable().getPageNumber() +1;
//        //-1값이 들어가는 것을 막기 위해서 max값으로 두 개의 값을 넣고 더 큰 값을 넣어주게 된다.
        int startPage =  Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage+9, gameCharacterRankPage.getTotalPages());

        model.addAttribute("gameCharacterRankList",gameCharacterRankPage);
        model.addAttribute("nowPage",nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "gameCharacter/gameCharacterRank";
    }
}
