package Park.gamewebpage.dto.freeboard.api;

import Park.gamewebpage.domain.FreeBoard;
import lombok.Getter;
import lombok.Setter;

/**
 * 블로그 글 가져오기
 * title,content,writerId,writerName
 */
@Getter
@Setter
public class GetFreeBoardDTO {
    private final String title;
    private final String content;
    private final String writerId;
    private final String writerName;

    public GetFreeBoardDTO(FreeBoard freeBoard){
        this.title = freeBoard.getTitle();
        this.content = freeBoard.getContent();
        this.writerId = freeBoard.getWriterId();
        this.writerName = freeBoard.getWriterName();
    }
}
