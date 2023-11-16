package Park.gamewebpage.controller;

import Park.gamewebpage.dto.hiscore.IGetRankByHighScoreDTO;
import Park.gamewebpage.service.GameHighScoreService;
import Park.gamewebpage.url.URL;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class GameHighScoreViewController {
    private  final GameHighScoreService gameHighScoreService;

    /**
     * 게임 캐릭터 랭크를 보여주는 컨트롤러
     * 페이징을 사용한다.
     * @param model
     * @param pageable
     * @return
     */
    @GetMapping(URL.GAME_CHARACTER_RANK_VIEW)
    public String getRankListGameCharacter(
            Model model,
            // @PageableDefault(현제페이지값, 한페이지당 허가되는 개수 , 정렬조건, 오름, 내림 차순)
            // pageable을 매개변수로 설정한 후 위와 같이 요청이 오면 JpaRepository는 그에 해당하는 Pageable 객체를 자동으로 만들어준다.
            // 즉 전체리스트를 그대로 가져오면 여기서 패이징을 해준다.
            @PageableDefault(page=0, size=10, sort="ranking", direction= Sort.Direction.DESC) Pageable pageable
    ){
        // 게임캐릭터 순위 리스트 정보 가져오기
        List<IGetRankByHighScoreDTO> gameCharacterRankList
                = gameHighScoreService.getGameCharacterRankList();
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<IGetRankByHighScoreDTO> gameCharacterRankPage
               = new PageImpl<>(gameCharacterRankList.subList(0, 10),pageRequest, gameCharacterRankList.size());

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
