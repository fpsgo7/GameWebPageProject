package Park.gamewebpage.dto;

import Park.gamewebpage.domain.FreeBoard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * FreeBoard 를 생성하기위한
 * DTO 클래스
 */
@NoArgsConstructor
@AllArgsConstructor
public class CreateFreeBoardDTO {

    private String title;
    private String content;
    private String writerId;
    private String writerName;

    /**
     * 빌더 패턴을 사용해 DTO를 d
     * @return
     */
    public FreeBoard toEntity(){
        return FreeBoard.builder()
                .title(title)
                .content(content)
                .writerId(writerId)
                .writerName(writerName)
                .build();
    }
}
