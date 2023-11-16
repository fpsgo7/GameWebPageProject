package Park.gamewebpage.controller;

import Park.gamewebpage.domain.GameCharacter;
import Park.gamewebpage.domain.GameHighScore;
import Park.gamewebpage.repository.IGameHighScoreRepository;
import Park.gamewebpage.url.URL;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GameHighScoreApiControllerTest {

    protected MockMvc mockMvc;

    @Autowired
    // 직렬화 역질렬화 를 위해 사용
    protected ObjectMapper objectMapper;

    @Autowired
    // 서블릿 컨텍스트를 사용하기 위해 사용
    private WebApplicationContext webApplicationContext;

    @Autowired
    IGameHighScoreRepository iGameHighScoreRepository;

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

        iGameHighScoreRepository.deleteAll();
    }

    @Test
    void getGameCharacterRankList() throws Exception {
        //given
        final String url = URL.GAME_CHARACTER_RANK_API;

        GameHighScore gameCharacter1 =
                iGameHighScoreRepository.save(
                        GameHighScore.builder()
                                .email("1")
                                .nickname("1")
                                .highScore(1)
                                .lastedTime(LocalDateTime.now())
                                .build());
        GameHighScore gameCharacter2 =
                iGameHighScoreRepository.save(
                        GameHighScore.builder()
                                .email("2")
                                .nickname("2")
                                .highScore(2)
                                .lastedTime(LocalDateTime.now())
                                .build());
        GameHighScore gameCharacter3 =
                iGameHighScoreRepository.save(
                        GameHighScore.builder()
                                .email("3")
                                .nickname("3")
                                .highScore(3)
                                .lastedTime(LocalDateTime.now())
                                .build());

        //when
        final ResultActions resultActions
                = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ranking").value("1"))
                .andExpect(jsonPath("$[1].ranking").value("2"))
                .andExpect(jsonPath("$[2].ranking").value("3"));

    }
}