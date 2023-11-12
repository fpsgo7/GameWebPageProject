package Park.gamewebpage.controller;

import Park.gamewebpage.config.jwt.JwtFactory;
import Park.gamewebpage.config.token.jwt.JwtProperties;
import Park.gamewebpage.domain.RefreshToken;
import Park.gamewebpage.domain.User;
import Park.gamewebpage.dto.token.CreateAccessTokenRequest;
import Park.gamewebpage.repository.IRefreshTokenRepository;
import Park.gamewebpage.repository.IUserRepository;
import Park.gamewebpage.url.URL;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TokenApiControllerTest {

    @Autowired
    protected MockMvc mockMvc;// mockMvc http를 이용한 테스트 위해사용
    @Autowired
    protected ObjectMapper objectMapper;// 직렬화 역직렬화 를 위해 사용
    @Autowired
    private WebApplicationContext context;
    @Autowired
    JwtProperties jwtProperties;
    @Autowired
    IUserRepository userRepository;
    @Autowired
    IRefreshTokenRepository refreshTokenRepository;

    @BeforeEach
    public void setMockMvc(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        userRepository.deleteAll();
    }

    /**
     * 새로운 액세스 토큰을 발급하는 것을 테스트합니다.
     */
    @DisplayName("createNewAccessToken : 새로운 액세스 토큰을 발급합니다.")
    @Test
    void createNewAccessToken() throws Exception {
        /**
         * given
         * 테스트 유저를 생성하고,
         * jjwt 라이브러리를 이용해
         * 리프레시 토큰을 만들어 데이터베이스에 저장한다.
         * 토큰 생성 API의 요청 본문에 리프레시 토큰을 포함하여
         * 요청 객체를 생성한다.
         */
        final String url = URL.TOKEN_API;
        // 테스트 유저 
        User testUser = userRepository.save(User.builder()
                .email("user@gmail.com")
                .password("test")
                .build());
        // 리프레시 토큰값 생성
        String refreshToken = JwtFactory.builder()
                .claims(Map.of("id",testUser.getId()))
                .build()
                .createToken(jwtProperties);
        // 리프레시 토큰 저장소에 저장한다. (즉 서버에 저장할 것이란 의미이다)
        refreshTokenRepository.save(new RefreshToken(
                testUser.getId(),refreshToken));

        // 액세스 토큰 요청하기
        CreateAccessTokenRequest request = new CreateAccessTokenRequest();
        request.setRefreshToken(refreshToken);
        final String requestBody = objectMapper.writeValueAsString(request);

        /**
         * when
         * 토큰 추가 API 에 요청을 보냅니다.
         * 이때 요청 타입은 JSON이며 given 절에서 미리 만들어둔
         * 객체를 요청 본문으로 함께 보낸다.
         */
        ResultActions resultActions = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        /**
         * then
         * 응답코드가 201 Created인지 확인하고
         * 응답으로 온 액세스 토큰이 비어 있지 않은지 확한다.
         */
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accessToken").isNotEmpty());
    }
}