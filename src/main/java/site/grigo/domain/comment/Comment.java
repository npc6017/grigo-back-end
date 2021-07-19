package site.grigo.domain.comment;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter @Setter
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Account id로 설정하는 것.
    private String writer;
    private String content;
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date timeStamp;
}
