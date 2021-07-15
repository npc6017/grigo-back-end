package site.grigo.domain.tag;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Tag {
    private Long id;
    private String name;
    private String category;

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
