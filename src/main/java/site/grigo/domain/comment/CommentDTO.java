package site.grigo.domain.comment;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class CommentDTO {
    private Long id;
    private String content;
    private String writer;
    private LocalDateTime timeStamp;
    private boolean userCheck = false;

    public CommentDTO(Long id, String content, String writer ,LocalDateTime timeStamp) {
        this.id = id;
        this.writer = writer;
        this.content = content;
        this.timeStamp = timeStamp;
    }
    public CommentDTO() {

    }
}
