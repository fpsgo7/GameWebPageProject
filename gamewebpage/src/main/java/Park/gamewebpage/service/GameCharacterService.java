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
    public List<GameCharacter> getGameCharacterList(){
        return iGameCharacterRepository.findAll();
    }

    // CREATE

    /**
     * 게임 캐릭터 생성
     * @param email 이메일 값
     * @param nickname 게임 케릭터 닉네임값
     * @return 게임 게릭터 객체
     */
    public GameCharacter createGameCharacter(String email, String nickname, Long highScore){
        GameCharacter character = new GameCharacter(email,nickname,highScore);
        iGameCharacterRepository.save(character);
        // 서버에 저장후 가져오는 방식을 사용하여 서버에 정상적으로 저장 됬는지 확인한다.
        character = iGameCharacterRepository.findByEmail(email).orElseThrow(() ->
                new IllegalArgumentException(
                        "Unexpected GameCharacter"));;
        return character;
    }

    // UPDATE

    /**
     * 게임 캐릭터의 점수를 수정한다 (향후 필요하면 사용한다.)
     */
    @Transactional // 게임 캐릭터를 찾는게 실패하면 작업을 취소한다.
    public void updateGameCharacterHighScore(String email, String score){
        GameCharacter gameCharacter
                = iGameCharacterRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Unexpected GameCharacter"));
        Long newScore = Long.parseLong(score);
        if(newScore > gameCharacter.getHighScore())
        gameCharacter.setHighScore(newScore);
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
