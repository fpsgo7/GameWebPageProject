package Park.gamewebpage.controller;

import Park.gamewebpage.domain.FreeBoard;
import Park.gamewebpage.domain.User;
import Park.gamewebpage.dto.freeboard.CreateFreeBoardDTO;
import Park.gamewebpage.dto.freeboard.UpdateFreeBoardDTO;
import Park.gamewebpage.repository.IFreeBoardRepository;
import Park.gamewebpage.repository.IUserRepository;
import Park.gamewebpage.url.URL;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 글 생성 관련 테스트를 위한
 * 파일이다.
 */
@SpringBootTest
@AutoConfigureMockMvc
class FreeBoardApiControllerTest {
    protected MockMvc mockMvc;

    @Autowired
    // 직렬화 역질렬화 를 위해 사용
    protected ObjectMapper objectMapper;

    @Autowired
    // 서블릿 컨텍스트를 사용하기 위해 사용
    private WebApplicationContext webApplicationContext;

    @Autowired
    IFreeBoardRepository iFreeBoardRepository;

    @Autowired
    IUserRepository iUserRepository;

    User user;

    /**
     * mockMvc 객체를 빌더 형식으로
     * 생성한후 받아오며
     * 자유 게시판 저장소를 청소한다.
     */
    @BeforeEach
    public void setMockMvc(){
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();

        iFreeBoardRepository.deleteAll();
    }

    @BeforeEach
    void setSecurityContext(){
        iUserRepository.deleteAll();
        user = iUserRepository.save(User.builder()
                .email("user@gmail.com")
                .password("test").build());
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities()));
    }

    @DisplayName("createFreeBoard: 자유게시판 글 추가에 성공하였다.")
    @Test
    public void createFreeBoard() throws Exception{
        // given
        // url 변수 생성
        final String url = URL.FREE_BOARD_API;

        // CreateFreeBoardDTO 객체에 담을 변수
        final String title = "타이틀1";
        final String content = "콘텐츠1";

        // 테스트에 사용할 CreateFreeBoardDTO 생성
        final CreateFreeBoardDTO createFreeBoardDTO
                = new CreateFreeBoardDTO(title,content);

        final String requestBody
                = objectMapper.writeValueAsString(createFreeBoardDTO);

        Principal principal = Mockito.mock(Principal.class);
        Mockito.when(principal.getName()).thenReturn("username");

        // when
        // 설정한 내용을 바타으로 요청을 전송한다.
        ResultActions result
                = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .principal(principal)
                .content(requestBody));

        // then
        // HTTP 응답코드가 isCreated 201 상태를 기대한다.
        result.andExpect(status().isCreated());
        List<FreeBoard> freeBoardList = iFreeBoardRepository.findAll();

        Assertions.assertThat(freeBoardList.size()).isEqualTo(1); // 크기가 1인지 체크
        Assertions.assertThat(freeBoardList.get(0).getTitle()).isEqualTo(title);
        Assertions.assertThat(freeBoardList.get(0).getContent()).isEqualTo(content);
    }


    @DisplayName("getListFreeBoard: 자유게시판 리스트 조회 성공.")
    @Test
    public void  getListFreeBoard()throws Exception{
        // given
        final String url = URL.FREE_BOARD_API;
        FreeBoard savedFreeBoard = createDefaultFreeBoard();

        // when
        final  ResultActions resultActions
                = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isOk())
                // jsonPath 로 json으로 변환된 freeBoardDTOList
                // 에서 가장 첫번쨰의 FreeBoard 객체의 값을
                // 가져온다.
                // andExpect는 결과를 기대하는 것으로
                // 바로 밑 문장은 왼쪽과 오른쪽 같이 같은지 확인한다
                .andExpect(jsonPath("$[0].content").value(savedFreeBoard.getContent()))
                .andExpect(jsonPath("$[0].title").value(savedFreeBoard.getTitle()));
    }

    @DisplayName("getFreeBoard: 자유게시판 글 조회 성공.")
    @Test
    public void  getFreeBoard()throws Exception{
        // given
        final String url = URL.FREE_BOARD_API_BY_ID;

        FreeBoard savedFreeBoard = createDefaultFreeBoard();
        //when
        final ResultActions resultActions
                = mockMvc.perform(get(
                        url,savedFreeBoard.getId()));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(savedFreeBoard.getContent()))
                .andExpect(jsonPath("$.title").value(savedFreeBoard.getTitle()))
                .andExpect(jsonPath("$.writerId").value(savedFreeBoard.getWriterId()));

    }

    @DisplayName("deleteFreeBoard: 자유 게시판 글 삭제에 성공한다.")
    @Test
    public void deleteFreeBoard() throws Exception{
        // given
        final String url = URL.FREE_BOARD_API_BY_ID;

        FreeBoard savedFreeBoard = createDefaultFreeBoard();

        //when
        // 삭제 작업 수행
        mockMvc.perform(delete(
                url, savedFreeBoard.getId()
        )).andExpect(status().isOk());

        //then
        List<FreeBoard> freeBoardList = iFreeBoardRepository.findAll();
        // 삭제가 잘되었다면 저장소는 비어있어야한다.
        Assertions.assertThat(freeBoardList).isEmpty();
    }

    @DisplayName("updateFreeBoard: 자유 게시판 글 수정에 성공한다.")
    @Test
    public void updateFreeBoard() throws Exception{
        //given
        final String url = URL.FREE_BOARD_API_BY_ID;

        FreeBoard savedFreeBoard = createDefaultFreeBoard();

        final String newTitle = "new title";
        final String newContent = "new content";

        UpdateFreeBoardDTO updateFreeBoardDTO
                = new UpdateFreeBoardDTO(newTitle,newContent);

        // when
        ResultActions resultActions
                = mockMvc.perform(put(url,savedFreeBoard.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(updateFreeBoardDTO)));

        // then
        resultActions.andExpect(status().isOk());

        FreeBoard freeBoard = iFreeBoardRepository.findById(savedFreeBoard.getId())
                .get();

        Assertions.assertThat(freeBoard.getTitle()).isEqualTo(newTitle);
        Assertions.assertThat(freeBoard.getContent()).isEqualTo(newContent);
    }

    private FreeBoard createDefaultFreeBoard(){
        return iFreeBoardRepository.save(FreeBoard.builder()
                .title("title")
                .writerId(user.getUsername())
                .content("content")
                .build());
    }
}