package Park.gamewebpage.domain;

import lombok.*;
import javax.persistence.*;

/**
 * 자유 게시판 글의 댓글을 위한 클래스이다.
 * 속성
 * id, comment, writer_id,created_date, modified_date
 * 으로 구성되어있다.
 */
@Table(name = "free_board_comment")
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FreeBoardComment extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",updatable = false)
    private Long id;

    @Column(name = "comment_text",nullable = false)
    private String comment;

    @Column(name = "writer_id",nullable = false)
    private String writerId;

    @ManyToOne
    @JoinColumn(name = "free_board_id", nullable = false)
    private FreeBoard freeBoard;


    @Builder
    public FreeBoardComment(String comment, String writerId, FreeBoard freeBoard) {
        this.comment = comment;
        this.writerId = writerId;
        this.freeBoard = freeBoard;
    }


}
