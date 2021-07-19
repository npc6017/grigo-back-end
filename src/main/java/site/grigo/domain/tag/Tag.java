package site.grigo.domain.tag;

import lombok.Getter;
import lombok.Setter;
import site.grigo.domain.posttag.PostTag;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Tag {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String category;
    @OneToMany(mappedBy = "tag")
    private List<PostTag> posts = new ArrayList<>();

    public Tag(String name, String category) {
        this.name = name;
        this.category = category;
    }

    public Tag(String name) {
        this.name = name;
    }

    public Tag() {
    }
}
