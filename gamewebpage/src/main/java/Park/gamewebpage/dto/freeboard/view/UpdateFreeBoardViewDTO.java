package Park.gamewebpage.dto.freeboard.view;

import Park.gamewebpage.domain.FreeBoard;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * 블로그 업데이트 뷰
 * id, title, content
 */
@Getter
@NoArgsConstructor
public class UpdateFreeBoardViewDTO {
    private Long id;
    private String title;
    private String content;

    public UpdateFreeBoardViewDTO(FreeBoard freeBoard){
        this.id = freeBoard.getId();
        this.title = freeBoard.getTitle();
        this.content = freeBoard.getContent();
    }
}
