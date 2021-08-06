package site.grigo.domain.notification;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NotificationDTO {

    private Long id;
    private Long postId;
    private String title;

    public NotificationDTO(Long id, Long postId, String title) {
        this.id = id;
        this.postId = postId;
        this.title = title;
    }
}
