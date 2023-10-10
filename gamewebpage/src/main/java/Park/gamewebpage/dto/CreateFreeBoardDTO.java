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
@Getter
@Setter
public class CreateFreeBoardDTO {

    private String title;
    private String content;
    private String writer;
    private String writerName;

    /**
     * 빌더 패턴을 사용해 DTO를 d
     * @return
     */
    public FreeBoard toEntity(){
        return FreeBoard.builder()
                .title(title)
                .content(content)
                .writer(writer)
                .writerName(writerName)
                .build();
    }
}
