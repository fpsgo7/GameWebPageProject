package Park.gamewebpage.controller;

import Park.gamewebpage.domain.FreeBoard;
import Park.gamewebpage.dto.CreateFreeBoardDTO;
import Park.gamewebpage.repository.IFreeBoardRepository;
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
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class FreeBoardControllerTest {
    protected MockMvc mockMvc;

    @Autowired
    // 직렬화 역질렬화 를 위해 사용
    protected ObjectMapper objectMapper;

    @Autowired
    // 서블릿 컨텍스트를 사용하기 위해 사용
    private WebApplicationContext webApplicationContext;

    @Autowired
    IFreeBoardRepository iFreeBoardRepository;

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

    @DisplayName("createFreeBoard: 자유게시판 글 추가에 성공하였다.")
    @Test
    public void createFreeBoard() throws Exception{
        // given
        // url 변수 생성
        final String url = "/freeBoard";

        // CreateFreeBoardDTO 객체에 담을 변수
        final String title = "타이틀1";
        final String content = "콘텐츠1";
        final String writer = "사용자아이디1";
        final String writerName = "사용자이름1";

        // 테스트에 사용할 CreateFreeBoardDTO 생성
        final CreateFreeBoardDTO createFreeBoardDTO
                = new CreateFreeBoardDTO(title,content,writer,writerName);

        final String requestBody
                = objectMapper.writeValueAsString(createFreeBoardDTO);

        // when
        // 설정한 내용을 바타으로 요청을 전송한다.
        ResultActions result
                = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        // then
        // HTTP 응답코드가 isCreated 201 상태를 기대한다.
        result.andExpect(status().isCreated());
        List<FreeBoard> freeBoardList = iFreeBoardRepository.findAll();

        Assertions.assertThat(freeBoardList.size()).isEqualTo(1); // 크기가 1인지 체크
        Assertions.assertThat(freeBoardList.get(0).getTitle()).isEqualTo(title);
        Assertions.assertThat(freeBoardList.get(0).getContent()).isEqualTo(content);
    }
}