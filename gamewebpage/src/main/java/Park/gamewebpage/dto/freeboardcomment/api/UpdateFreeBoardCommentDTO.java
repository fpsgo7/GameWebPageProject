package Park.gamewebpage.dto.freeboardcomment.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 자유게시판 댓글 수정하기
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateFreeBoardCommentDTO {
    private String comment;
}
