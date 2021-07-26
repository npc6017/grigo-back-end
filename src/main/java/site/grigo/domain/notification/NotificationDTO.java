package site.grigo.domain.notification;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NotificationDTO {

    private Long id;
    private Long postId;
    private String tag;

    public NotificationDTO(Long id, Long postId, String tag) {
        this.id = id;
        this.postId = postId;
        this.tag = tag;
    }
}
