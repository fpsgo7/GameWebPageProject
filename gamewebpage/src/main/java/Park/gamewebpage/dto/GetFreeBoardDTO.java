package Park.gamewebpage.dto;

import Park.gamewebpage.domain.FreeBoard;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetFreeBoardDTO {
    private final String TITLE;
    private final String CONTENT;
    private final String WRITER_ID;
    private final String WRITER_NAME;

    public GetFreeBoardDTO(FreeBoard freeBoard){
        this.TITLE = freeBoard.getTitle();
        this.CONTENT = freeBoard.getContent();
        this.WRITER_ID = freeBoard.getWriterId();
        this.WRITER_NAME = freeBoard.getWriterName();
    }
}
