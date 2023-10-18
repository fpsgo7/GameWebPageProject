package Park.gamewebpage.dto.freeboard.api;

import Park.gamewebpage.domain.FreeBoard;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;

/**
 * 블고그 생성용
 * title,content,writerId,writerName
 */
@NoArgsConstructor
@AllArgsConstructor
// Test의 writeValueAsString 가 해당 클래스의 생성자를 찾지 못해
// 해당 애너테이션을 적용하였다.
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CreateFreeBoardDTO {

    private String title;
    private String content;
    private String writerId;
    private String writerName;

    /**
     * 빌더 패턴을 사용해 DTO를 생성
     *  글 추가할 때 저장할
     *  엔티티로 변환하는 용도로 사용할 것이다.
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
