package Park.gamewebpage.dto.freeboard.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 블로그 객체 수정하기
 * title,content
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateFreeBoardDTO {
    private String title;
    private String content;
}
