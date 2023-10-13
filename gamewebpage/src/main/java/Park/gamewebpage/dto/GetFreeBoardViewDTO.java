package Park.gamewebpage.dto;

import Park.gamewebpage.domain.FreeBoard;
import lombok.Getter;
import lombok.Setter;

/**
 * 블로그글 조회 뷰 제작때 미처 추가하지 못한 필드 변수 2개 추가할것!
 */
@Getter
@Setter
public class GetFreeBoardViewDTO {
    private final Long id;
    private final String title;
    private final String content;
    private final String writerId;
    private final String writerName;

    public GetFreeBoardViewDTO(FreeBoard freeBoard){
        this.id = freeBoard.getId();
        this.title = freeBoard.getTitle();
        this.content = freeBoard.getContent();
        this.writerId = freeBoard.getWriterId();
        this.writerName = freeBoard.getWriterName();
    }
}
