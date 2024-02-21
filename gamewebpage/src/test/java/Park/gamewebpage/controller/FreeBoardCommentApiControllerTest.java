package Park.gamewebpage.controller;

import Park.gamewebpage.config.oauth.CustomUserDetails;
import Park.gamewebpage.domain.FreeBoard;
import Park.gamewebpage.domain.FreeBoardComment;
import Park.gamewebpage.domain.User;
import Park.gamewebpage.dto.freeboardcomment.CreateFreeBoardCommentDTO;
import Park.gamewebpage.repository.IFreeBoardCommentRepository;
import Park.gamewebpage.repository.IFreeBoardRepository;
import Park.gamewebpage.repository.IUserRepository;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FreeBoardCommentApiControllerTest {

    protected MockMvc mockMvc;

    @Autowired
    // 직렬화 역질렬화 를 위해 사용
    protected ObjectMapper objectMapper;

    @Autowired
    // 서블릿 컨텍스트를 사용하기 위해 사용
    private WebApplicationContext webApplicationContext;

    @Autowired
    IFreeBoardCommentRepository iFreeBoardCommentRepository;

    @Autowired
    IFreeBoardRepository iFreeBoardRepository;

    @Autowired
    IUserRepository iUserRepository;

    User user;

    CustomUserDetails userDetails;

    /**
     * mockMvc 객체를 빌더 형식으로
     * 생성한후 받아오며
     * 게시판 댓글 저장소를 청소한다.
     */
    @BeforeEach
    public void setMockMvc(){
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();

        iFreeBoardRepository.deleteAll();
        iFreeBoardCommentRepository.deleteAll();
    }

    /**
     * 테스트에 사용할 계정을 생성해준다.
     */
    @BeforeEach
    void setSecurityContext(){
        iUserRepository.deleteAll();
        user = iUserRepository.save(User.builder()
                .email("user@gmail.com")
                .password("test").build());
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getPassword(), userDetails.getAuthorities()));
    }

    /**
     * 댓글을 달 대상인  자유게시판 글 이다.
     * @throws Exception
     */
    public FreeBoard createFreeBoard() throws Exception{
        // CreateFreeBoardDTO 객체에 담을 변수
        final String title = "타이틀1";
        final String content = "콘텐츠1";

        return iFreeBoardRepository.save(FreeBoard.builder()
                .title("title")
                .writerId(userDetails.getUsername())
                .content("content")
                .build());
    }

    @DisplayName("createFreeBoard: 자유게시판 글 추가에 성공하였다.")
    @Test
    void createFreeBoardComment() throws Exception{
        // 자유게시판 하나 생성후 시작
        FreeBoard freeBoard = createFreeBoard();
        // given
        // url 변수 생성
        // 미리생성된 자유게시판을 대상으로 한다.
        final String url = "/api/freeBoard/"+freeBoard.getId()+"/freeBoardComment";

        final String comment = "코맨트";

        final CreateFreeBoardCommentDTO createFreeBoardCommentDTO
                = new CreateFreeBoardCommentDTO(comment,freeBoard);

        final String requestBody
                = objectMapper.writeValueAsString(createFreeBoardCommentDTO);

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
        List<FreeBoardComment> freeBoardCommentList = iFreeBoardCommentRepository.findAll();

        Assertions.assertThat(freeBoardCommentList.size()).isEqualTo(1); // 크기가 1인지 체크
        Assertions.assertThat(freeBoardCommentList.get(0).getComment()).isEqualTo(comment);
    }

}