package Park.gamewebpage.dto.freeboard.view;

import Park.gamewebpage.domain.FreeBoard;
import lombok.Getter;
import lombok.Setter;

/**
 * 블로그 글 뷰 리스트
 * id, title, content, writerId, writerName
 */
@Getter
@Setter
public class GetFreeBoardViewListDTO {
    private final Long id;
    private final String title;
    private final String content;
    private final String writerId;

    public GetFreeBoardViewListDTO(FreeBoard freeBoard){
        this.id = freeBoard.getId();
        this.title = freeBoard.getTitle();
        this.content = freeBoard.getContent();
        this.writerId = freeBoard.getWriterId();
    }
}
