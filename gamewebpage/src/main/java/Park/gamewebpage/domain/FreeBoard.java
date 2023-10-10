package Park.gamewebpage.domain;

import lombok.*;

import javax.persistence.*;

/**
 * 자유 게시판 글 엔티티를 위한 클래스이다.
 * 속성
 * id, title, content, writer, writerName
 * 으로 구성되어있다.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FreeBoard {

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

    @Column(name = "writer",nullable = false)
    private String writer;

    @Column(name = "writerName",nullable = false)
    private String writerName;

    /**
     * 기본적인 생성자가 아닌
     * Builder 방식으로 객체를
     * 생성하고자 싶은경우 사용한다.
     * @param title
     * @param content
     * @param writer
     * @param writerName
     */
    @Builder
    public FreeBoard(String title, String content, String writer, String writerName){
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.writerName = writerName;
    }
}
