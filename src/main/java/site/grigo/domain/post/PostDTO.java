package site.grigo.domain.post;

import lombok.Getter;
import lombok.Setter;
import site.grigo.domain.comment.CommentDTO;
import site.grigo.domain.posttag.PostTag;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class PostDTO {
    private Long id;
    private String title;
    private String writer;
    private String content;
    private String boardType;
    private List<String> tags = new ArrayList<>();
    private List<CommentDTO> comments = new ArrayList<>();
    private LocalDateTime timeStamp;
    private boolean userCheck = false;

    public PostDTO(Long id, String title, String writer, String content, String boardType, List<String> tag, LocalDateTime timeStamp) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.boardType = boardType;
        this.tags = tag;
        this.timeStamp = timeStamp;
    }

    public PostDTO(Long id, String title, String writer, String content, String boardType, List<String> tags, List<CommentDTO> comments, LocalDateTime timeStamp) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.boardType = boardType;
        this.tags = tags;
        this.comments = comments;
        this.timeStamp = timeStamp;
    }

    private List<String> toStringTags(List<PostTag> pts) {
        List<String> tags = new ArrayList<>();
        for(PostTag pt : pts)
            tags.add(pt.getTag().getName());
        return tags;
    }

    public PostDTO() {

    }
}
