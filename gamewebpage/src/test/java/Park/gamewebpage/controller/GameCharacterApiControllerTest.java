package Park.gamewebpage.controller;

import Park.gamewebpage.domain.GameCharacter;
import Park.gamewebpage.dto.Character.GameCharacterRequestDTO;
import Park.gamewebpage.repository.IGameCharacterRepository;
import Park.gamewebpage.url.URL;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GameCharacterApiControllerTest {
    protected MockMvc mockMvc;

    @Autowired
    // 직렬화 역질렬화 를 위해 사용
    protected ObjectMapper objectMapper;

    @Autowired
    // 서블릿 컨텍스트를 사용하기 위해 사용
    private WebApplicationContext webApplicationContext;

    @Autowired
    IGameCharacterRepository iGameCharacterRepository;

    /**
     * mockMvc 객체를 빌더 형식으로
     * 생성한후 받아오며
     * 게임 캐릭터 저장소를 청소한다.
     */
    @BeforeEach
    public void setMockMvc(){
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();

        iGameCharacterRepository.deleteAll();
    }

    @DisplayName("게임 캐릭터 정보 가져오기")
    @Test
    void getGameCharacter() throws Exception {
        //given
        final String url = URL.GAME_CHARACTER_API_BY_EMAIL;

        GameCharacter gameCharacter =
                iGameCharacterRepository.save(
                GameCharacter.builder()
                        .email("1")
                        .nickname("1")
                        .highScore(1)
                        .build());
        //when
        final ResultActions resultActions
                = mockMvc.perform(get(url,gameCharacter.getEmail())
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("1"))
                .andExpect(jsonPath("$.nickname").value("1"))
                .andExpect(jsonPath("$.highScore").value(1));
    }

    @DisplayName("게임 캐릭터리스트 정보 가져오기")
    @Test
    void getGameCharacterList() throws Exception{

        //given
        final String url = URL.GAME_CHARACTER_API;

        GameCharacter gameCharacter1 =
                iGameCharacterRepository.save(
                        GameCharacter.builder()
                                .email("1")
                                .nickname("1")
                                .highScore(1)
                                .build());
        GameCharacter gameCharacter2 =
                iGameCharacterRepository.save(
                        GameCharacter.builder()
                                .email("2")
                                .nickname("2")
                                .highScore(2)
                                .build());

        //when
        final ResultActions resultActions
                = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("1"))
                .andExpect(jsonPath("$[1].email").value("2"));

    }

    @DisplayName("게임 캐릭터리 닉네임 수정하기")
    @Test
    void updateNickNameGameCharacter() throws Exception{
        //given
        final String url = URL.GAME_CHARACTER_API_BY_EMAIL;
        final String oldNickname="old";
        final String newNickname="hello";

        GameCharacter gameCharacter =
                iGameCharacterRepository.save(
                        GameCharacter.builder()
                                .email("1")
                                .nickname(oldNickname)
                                .highScore(1)
                                .build());

        GameCharacterRequestDTO gameCharacterRequestDTO
                = new GameCharacterRequestDTO(0, newNickname);
        //when
        ResultActions resultActions
                = mockMvc.perform(put(url,gameCharacter.getEmail())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(gameCharacterRequestDTO)));

        // then
        resultActions.andExpect(status().isOk());
        GameCharacter updatedGameCharacter = iGameCharacterRepository.findByEmail(gameCharacter.getEmail())
                .get();
        Assertions.assertThat(updatedGameCharacter.getNickname())
                .isEqualTo(newNickname);
    }

    @DisplayName("게임 캐릭터 삭제하기")
    @Test
    void deleteGameCharacter() throws Exception{
        //given
        final String url = URL.GAME_CHARACTER_API_BY_EMAIL;

        GameCharacter gameCharacter =
                iGameCharacterRepository.save(
                        GameCharacter.builder()
                                .email("1")
                                .nickname("1")
                                .highScore(1)
                                .build());
        //when
        // 삭제 작업 수행
        mockMvc.perform(delete(
                url, gameCharacter.getEmail()
        )).andExpect(status().isOk());

        //then
        List<GameCharacter> gameCharacterList = iGameCharacterRepository.findAll();
        // 삭제가 잘되었다면 저장소는 비어있어야한다.
        Assertions.assertThat(gameCharacterList).isEmpty();
    }
}