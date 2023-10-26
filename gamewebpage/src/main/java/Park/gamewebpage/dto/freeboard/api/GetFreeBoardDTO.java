package Park.gamewebpage.dto.freeboard.api;

import Park.gamewebpage.domain.FreeBoard;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 블로그 글 가져오기
 * title,content,writerId,writerName
 */
@Getter
@Setter
public class GetFreeBoardDTO {
    private Long id;
    private final String title;
    private final String content;
    private final String writerId;
    private LocalDateTime createdDate;

    public GetFreeBoardDTO(FreeBoard freeBoard){
        this.id =freeBoard.getId();
        this.title = freeBoard.getTitle();
        this.content = freeBoard.getContent();
        this.writerId = freeBoard.getWriterId();
        this.createdDate = freeBoard.getCreatedDate();
    }
}
