package site.grigo.domain.post;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 페이징을 위한 class,
 * hasNext는 다음 받아올 데이터가 있는지 확인하는 변수이다. 쓸데없는 통신을 줄여준다.
 * posts는 return 해줄 postDTO 데이터들을 의미한다.
 * T는 PostDTO로 변경해도 될 거 같음.
 */

@Getter @Setter
public class CursorPage<T> {
    private List<T> postDTOS;
    private Boolean hasNext;

    public CursorPage(List<T> postDTOS, Boolean hasNext) {
        this.postDTOS = postDTOS;
        this.hasNext = hasNext;
    }
}
