package Park.gamewebpage.dto.freeboardDTO.view;

import Park.gamewebpage.domain.FreeBoard;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

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
