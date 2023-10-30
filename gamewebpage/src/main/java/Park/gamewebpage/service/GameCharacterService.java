package Park.gamewebpage.service;

import Park.gamewebpage.domain.GameCharacter;
import Park.gamewebpage.dto.Character.CreateGameCracterDTO;
import Park.gamewebpage.dto.Character.UpdateGameCharacterDTO;
import Park.gamewebpage.repository.IGameCharacterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 게임 캐릭터 서비스 이다.
 */
@RequiredArgsConstructor
@Service
public class GameCharacterService {

    private final IGameCharacterRepository iGameCharacterRepository;

    // CREATE

    /**
     * 게임 캐릭터 생성
     * 회원가입과 동시에 생성된다.
     * 아직까지는 동시에 생성되지 못함
     * @param createGameCracterDTO
     * @return 이메일 값
     */
    public Long createGameCharacter(CreateGameCracterDTO createGameCracterDTO){
        return iGameCharacterRepository
                .save(GameCharacter.builder()
                        .email(createGameCracterDTO.getEmail())
                        .nickname(createGameCracterDTO.getNickname())
                        .highScore(createGameCracterDTO.getHighScore())
                        .build()).getId();
    }

    // READ

    /**
     * 이메일로 게임 캐릭터를 찾는다.
     * @param email
     * @return 게임 캐릭터 객체
     */
    public GameCharacter findByEmail(String email){
        return iGameCharacterRepository.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Unexpected GameCharacter"));

    }
    // UPDATE

    /**
     * 게임 캐릭터의 점수를 변경한다.
     * 아직 기능 테스트 단계이며
     * 실현단계는 아니다.
     * @param updateGameCharacterDTO
     * @return 게임 캐릭터 객체
     */
    @Transactional // 게임 캐릭터를 찾는게 실패하면 작업을 취소한다.
    public GameCharacter updateGameCharacterHighScore(UpdateGameCharacterDTO updateGameCharacterDTO){
        GameCharacter gameCharacter
                = iGameCharacterRepository
                .findByEmail(updateGameCharacterDTO.getEmail())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Unexpected GameCharacter"));

        gameCharacter
                .setHighScore(
                        updateGameCharacterDTO
                                .getHighScore());
        return gameCharacter;
    }
    
    /**
     * 닉네임을 변경한다.
     * 유저닉네임이 변경되면 같이 적용된다.
     * @param updateGameCharacterDTO
     * @return 게임 케릭터 객체
     */
    @Transactional // 게임 캐릭터를 찾는게 실패하면 작업을 취소한다.
    public GameCharacter updateNickNameHighScore(UpdateGameCharacterDTO updateGameCharacterDTO){
        GameCharacter gameCharacter
                = iGameCharacterRepository
                .findByEmail(updateGameCharacterDTO.getEmail())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Unexpected GameCharacter"));

        gameCharacter
                .setNickname(
                        updateGameCharacterDTO
                                .getNickName());
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
