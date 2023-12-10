package Park.gamewebpage.dto.freeboard;

import Park.gamewebpage.domain.FreeBoard;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;

/**
 * 자유게시판 글 생성용
 * title,content,writerId,writerName
 * 게시판 글추가에 글 작성날짜를 넣어야할 것같지만
 * 글 작성 날짜와 수정 시간은 엔티티 클래스 수정으로
 * 스프링에 의하여 데이터베이스에 자동 입력된다
 */
@NoArgsConstructor
@AllArgsConstructor
// Test의 writeValueAsString 가 해당 클래스의 생성자를 찾지 못해
// 해당 애너테이션을 적용하였다.
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CreateFreeBoardDTO {

    private String title;
    private String content;

    /**
     * 빌더 패턴을 사용해 DTO를 생성
     *  글 추가할 때 저장할
     *  엔티티로 변환하는 용도로 사용할 것이다.
     * @return
     */
    public FreeBoard toEntity(String writerId){
        return FreeBoard.builder()
                .title(title)
                .content(content)
                .writerId(writerId)
                .build();
    }
}
