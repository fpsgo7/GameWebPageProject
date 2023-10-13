package Park.gamewebpage.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Column(name = "writerId",nullable = false)
    private String writerId;

    @Column(name = "writerName",nullable = false)
    private String writerName;

    @CreatedDate // 엔티티가 생성되면 생성시간이 자동으로 저장된다.
    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @LastModifiedDate // 엔티티가 수정될 떄 수정시간 저장
    @Column(name = "updatedAt")
    private String updatedAt;

    /**
     * 기본적인 생성자가 아닌
     * Builder 방식으로 객체를
     * 생성하고자 싶은경우 사용한다.
     * @param title
     * @param content
     * @param writerId
     * @param writerName
     */
    @Builder
    public FreeBoard(String title, String content, String writerId, String writerName){
        this.title = title;
        this.content = content;
        this.writerId = writerId;
        this.writerName = writerName;
    }
}
