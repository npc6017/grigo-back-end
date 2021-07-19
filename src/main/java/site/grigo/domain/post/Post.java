package site.grigo.domain.post;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import site.grigo.domain.posttag.PostTag;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Setter @Getter
public class Post {
    // GenerationType을 seqeunce나 table로 변경할 필요가 있을지 확인.
    // saveAll과 같은 데이터 입력이 생길 수 있나 생각해보고 변경할지말지 결정.
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    // Account의 id로 변경
    private String writer;
    private String content;
    @OneToMany(mappedBy = "post")
    private List<PostTag> tags = new ArrayList<>();
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date timeStamp;
}
