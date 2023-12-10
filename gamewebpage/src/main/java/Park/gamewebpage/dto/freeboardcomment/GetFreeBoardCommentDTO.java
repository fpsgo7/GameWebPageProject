package Park.gamewebpage.dto.freeboardcomment;

import Park.gamewebpage.domain.FreeBoard;
import Park.gamewebpage.domain.FreeBoardComment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class GetFreeBoardCommentDTO {
    private Long id;
    private String comment;
    private String writerId;
    private FreeBoard freeBoard;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public GetFreeBoardCommentDTO(FreeBoardComment freeBoardComment) {
        this.id = freeBoardComment.getId();
        this.comment = freeBoardComment.getComment();
        this.writerId = freeBoardComment.getWriterId();
        this.freeBoard = freeBoardComment.getFreeBoard();
        this.createdDate = freeBoardComment.getCreatedDate();
        this.modifiedDate = freeBoardComment.getModifiedDate();
    }
}
