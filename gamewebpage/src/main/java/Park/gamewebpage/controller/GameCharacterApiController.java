package Park.gamewebpage.controller;

import Park.gamewebpage.domain.GameCharacter;
import Park.gamewebpage.dto.Character.GetGameCharacterDTO;
import Park.gamewebpage.dto.Character.IGetGameCharacterRankDTO;
import Park.gamewebpage.dto.Character.UpdateGameCharacterNicknameDTO;
import Park.gamewebpage.service.GameCharacterService;
import Park.gamewebpage.url.URL;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class GameCharacterApiController {
    private  final GameCharacterService gameCharacterService;

    /**
     * 게임 캐릭터를 조회한다
     * 만약 게임캐릭터가 없다면 null을 반환시킨다.
     * @param email
     * @return 게임 캐릭터 객체 또는 null
     */
    @GetMapping(URL.GAME_CHARACTER_API_BY_EMAIL)
    public ResponseEntity<GetGameCharacterDTO> getGameCharacter(
            @PathVariable String email
    ){
        GameCharacter gameCharacter = gameCharacterService.findByEmail(email);

        if(gameCharacter != null){
            return ResponseEntity.ok()
                    .body(new GetGameCharacterDTO(gameCharacter));
        }else {
            return null;
        }
    }

    /**
     * 게임 케릭터 전체 조회 메서드이다.
     * @return 게임 캐릭터 리스트
     */
    @GetMapping(URL.GAME_CHARACTER_API)
    public ResponseEntity<List<GetGameCharacterDTO>> getGameCharacterList(){
        List<GetGameCharacterDTO> GameCharacterList
                = gameCharacterService.getGameCharacterList()
                .stream()
                .map(GetGameCharacterDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(GameCharacterList);
    }

    /**
     * 게임 케릭터 순위 전체 조회 메서드이다.
     * @return 게임 캐릭터 리스트
     */
    @GetMapping(URL.GAME_CHARACTER_RANK_API)
    public ResponseEntity<List<IGetGameCharacterRankDTO>> getGameCharacterRankList(){
        List<IGetGameCharacterRankDTO> GameCharacterList
                = gameCharacterService.getGameCharacterRankList();

        return ResponseEntity
                .ok()
                .body(GameCharacterList);
    }

    /**
     * 게임 캐릭터의 닉네임을 수정한다.
     * @param email
     * @param updateGameCharacterApiDTO
     * @return 게임 캐릭터 객체
     */
    @PutMapping(URL.GAME_CHARACTER_API_BY_EMAIL)
    public ResponseEntity<GameCharacter> updateNickNameGameCharacter(
            @PathVariable String email,
            @RequestBody UpdateGameCharacterNicknameDTO updateGameCharacterApiDTO
    ){
        GameCharacter gameCharacter
                = gameCharacterService
                .updateGameCharacterNickName(email,updateGameCharacterApiDTO);
        return ResponseEntity
                .ok()
                .body(gameCharacter);
    }

    /**
     * 게임 캐릭터를 삭제하는 메서드이다.
     * @param email
     * @return 성공 실패값
     */
    @DeleteMapping(URL.GAME_CHARACTER_API_BY_EMAIL)
    public ResponseEntity<Void> deleteGameCharacter(
            @PathVariable String email
    ){
        gameCharacterService.deleteGameCharacter(email);
        return ResponseEntity
                .ok()
                .build();
    }
}
