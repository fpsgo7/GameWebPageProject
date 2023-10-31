package Park.gamewebpage.service;

import Park.gamewebpage.domain.GameCharacter;
import Park.gamewebpage.dto.Character.UpdateGameCharacterHighScoreDTO;
import Park.gamewebpage.dto.Character.UpdateGameCharacterNicknameDTO;
import Park.gamewebpage.repository.IGameCharacterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 게임 캐릭터 서비스 이다.
 */
@RequiredArgsConstructor
@Service
public class GameCharacterService {

    private final IGameCharacterRepository iGameCharacterRepository;

    // READ

    /**
     * 이메일로 게임 캐릭터를 찾는다.
     * 게임 캐릭터가 없으면 아직 생성되지 않았다고 뜨게한다.
     * @param email
     * @return 게임 캐릭터 객체
     */
    public GameCharacter findByEmail(String email){
        return iGameCharacterRepository.findByEmail(email)
                .orElse(null);
    }

    /**
     * 게임 캐릭터 리스트를
     * 가져온다.
     * @return 게임 캐릭터 전체 조회 결과
     */
    public List<GameCharacter> getListGameCharacter(){
        return iGameCharacterRepository.findAll();
    }

    // UPDATE

    /**
     * 게임 캐릭터의 점수를 수정한다 (향후 필요하면 사용한다.)
     * @param updateGameCharacterNicknameDTO
     * @return 게임 캐릭터 객체
     */
    @Transactional // 게임 캐릭터를 찾는게 실패하면 작업을 취소한다.
    public GameCharacter updateGameCharacterHighScore(String email, UpdateGameCharacterHighScoreDTO updateGameCharacterNicknameDTO){
        GameCharacter gameCharacter
                = iGameCharacterRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Unexpected GameCharacter"));

        gameCharacter
                .setHighScore(
                        updateGameCharacterNicknameDTO
                                .getHighScore());
        return gameCharacter;
    }
    
    /**
     * 닉네임을 변경한다.
     * @param updateGameCharacterNicknameDTO
     * @return 게임 케릭터 객체
     */
    @Transactional // 게임 캐릭터를 찾는게 실패하면 작업을 취소한다.
    public GameCharacter updateGameCharacterNickName(String email
            , UpdateGameCharacterNicknameDTO updateGameCharacterNicknameDTO){
        GameCharacter gameCharacter
                = iGameCharacterRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Unexpected GameCharacter"));

        gameCharacter
                .setNickname(
                        updateGameCharacterNicknameDTO
                                .getNickname());
        return gameCharacter;
    }

    // DELETE

    /**
     * 회원 탈퇴시 게임 캐릭터를 삭제한다. 
     * 아직 미사용
     */
    @Transactional // 게임 캐릭터를 찾는게 실패하면 작업을 취소한다.
    public void deleteGameCharacter( String email ){
        GameCharacter gameCharacter
                = iGameCharacterRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Unexpected GameCharacter"));

        iGameCharacterRepository.deleteByEmail(email);
    }
}
