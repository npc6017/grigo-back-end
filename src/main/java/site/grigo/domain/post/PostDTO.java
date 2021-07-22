package site.grigo.domain.post;

import lombok.Getter;
import lombok.Setter;
import site.grigo.domain.posttag.PostTag;
import site.grigo.domain.tag.Tag;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter @Setter
public class PostDTO {
    private Long id;
    private String title;
    private String writer;
    private String content;
    private List<String> tag = new ArrayList<>();
    private Date timeStamp;

    public PostDTO(Long id, String title, String writer, String content, List<String> tags, Date timeStamp) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.tag = tags;
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
