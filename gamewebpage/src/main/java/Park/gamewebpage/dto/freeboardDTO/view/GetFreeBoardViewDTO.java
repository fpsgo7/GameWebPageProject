package Park.gamewebpage.dto.freeboardDTO.view;

import Park.gamewebpage.domain.FreeBoard;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 블로그 글 뷰
 * id,title, content,createAt
 */
@NoArgsConstructor
@Getter
public class GetFreeBoardViewDTO {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;

    public GetFreeBoardViewDTO(FreeBoard freeBoard){
        this.id = freeBoard.getId();
        this.title = freeBoard.getTitle();
        this.content = freeBoard.getContent();
        this.createdAt = freeBoard.getCreatedDate();
    }

}
