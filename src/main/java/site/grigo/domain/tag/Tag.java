package site.grigo.domain.tag;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Tag {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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
