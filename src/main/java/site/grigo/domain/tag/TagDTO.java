package site.grigo.domain.tag;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TagDTO {
    private String tagName;

    public TagDTO(String name) {
        this.tagName = name;
    }
}
