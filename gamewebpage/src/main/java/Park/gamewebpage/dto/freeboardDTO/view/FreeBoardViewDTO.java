package Park.gamewebpage.dto.freeboardDTO.view;

import Park.gamewebpage.domain.FreeBoard;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class FreeBoardViewDTO {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;

    public FreeBoardViewDTO(FreeBoard freeBoard){
        this.id = freeBoard.getId();
        this.title = freeBoard.getTitle();
        this.content = freeBoard.getContent();
        this.createdAt = freeBoard.getCreatedDate();
    }

}
