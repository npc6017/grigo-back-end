package site.grigo.domain.comment;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class CommentDTO {
    private Long id;
    private String content;
    private LocalDateTime timeStamp;

    public CommentDTO(Long id, String content, LocalDateTime timeStamp) {
        this.id = id;
        this.content = content;
        this.timeStamp = timeStamp;
    }
    public CommentDTO() {

    }
}
