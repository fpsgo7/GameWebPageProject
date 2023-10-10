package Park.gamewebpage.dto;

import Park.gamewebpage.domain.FreeBoard;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetListFreeBoardDTO {
    private final String title;
    private final String content;
    private final String writerId;
    private final String writerName;

    public GetListFreeBoardDTO(FreeBoard freeBoard){
        this.title = freeBoard.getTitle();
        this.content = freeBoard.getContent();
        this.writerId = freeBoard.getWriterId();
        this.writerName = freeBoard.getWriterName();
    }
}
