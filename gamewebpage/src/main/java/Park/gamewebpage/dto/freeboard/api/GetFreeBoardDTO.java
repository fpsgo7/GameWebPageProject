package Park.gamewebpage.dto.freeboard.api;

import Park.gamewebpage.domain.FreeBoard;
import Park.gamewebpage.domain.FreeBoardComment;
import Park.gamewebpage.dto.freeboardcomment.GetFreeBoardCommentDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 블로그 글 가져오기
 * title,content,writerId,writerName
 */
@Getter
@Setter
public class GetFreeBoardDTO {
    private Long id;
    private String title;
    private String content;
    private String writerId;
    private LocalDateTime createdDate;
    private List<GetFreeBoardCommentDTO> freeBoardComments;

    public GetFreeBoardDTO(FreeBoard freeBoard){
        this.id =freeBoard.getId();
        this.title = freeBoard.getTitle();
        this.content = freeBoard.getContent();
        this.writerId = freeBoard.getWriterId();
        this.createdDate = freeBoard.getCreatedDate();
        // 댓글들 가져오기
        this.freeBoardComments = freeBoard.getComments()
                .stream()
                .map(GetFreeBoardCommentDTO::new)
                .collect(Collectors.toList());
    }
}
