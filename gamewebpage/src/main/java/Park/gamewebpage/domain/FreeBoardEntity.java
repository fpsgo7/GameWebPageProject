package Park.gamewebpage.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * 자유 게시판 글 엔티티 클래스이다.
 * 속성
 * id, title, content, writer, writerName
 * 으로 구성되어있다.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FreeBoardEntity {

    /**
     * 기본키 이며 컬럼 이름은 id,
     * updatable은 false로 하여 수정을 막는다.
     * 자료형은 Long이다.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",updatable = false)
    private Long id;

    @Column(name = "title",nullable = false)
    private String title;

    @Column(name = "content",nullable = false)
    private String content;


}
