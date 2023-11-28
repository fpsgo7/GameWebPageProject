package Park.gamewebpage.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 자유 게시판 글 엔티티를 위한 클래스이다.
 * 속성
 * id, title, content, writer, writerName
 * 으로 구성되어있다.
 */
@Table(name = "free_board")
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FreeBoard extends BaseTimeEntity{

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

    @Column(name = "writer_id",nullable = false)
    private String writerId;

    /**
     * fetch = FetchType.EAGER : 게시글 UI에서 댓글을 바로 보여주기 위해 FetchType을 EAGER로 설정해줬다
     * 게시글이 삭제되면 댓글 또한 삭제되어야 하기 때문에 CascadeType.REMOVE 속성 지정
     * @OrderBy 어노테이션을 이용하여 간단히 정렬 처리
     */
    @OneToMany(mappedBy = "posts", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("id asc") // 댓글 오름차순 정렬
    private List<FreeBoardComment> comments;


    /**
     * 기본적인 생성자가 아닌
     * Builder 방식으로 객체를
     * 생성하고자 싶은경우 사용한다.
     * @param title
     * @param content
     * @param writerId
     */
    @Builder
    public FreeBoard(String title, String content, String writerId){
        this.title = title;
        this.content = content;
        this.writerId = writerId;
    }
}
