package Park.gamewebpage.dto.freeboardcomment;

import Park.gamewebpage.domain.FreeBoard;
import Park.gamewebpage.domain.FreeBoardComment;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 자유 게시판 댓글 생성용
 * comment,writerId
 * 작성 날짜는 엔티티 설정으로 자동 입력되니
 * 필요 X
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
// Test의 writeValueAsString 가 해당 클래스의 생성자를 찾지 못해
// 해당 애너테이션을 적용하였다.
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CreateFreeBoardCommentDTO {

    private String comment;
    private FreeBoard freeBoard;

    public FreeBoardComment toEntity(String writerId){
        return FreeBoardComment.builder()
                .comment(comment)
                .freeBoard(freeBoard)
                .writerId(writerId)
                .build();
    }
}
