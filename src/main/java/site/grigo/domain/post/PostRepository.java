package site.grigo.domain.post;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findById(Long postId);
    List<Post> findAllByBoardType(String boardType);

    /** DB에서 BoardType으로 검색한 후에 내림차순으로 첫번째 페이지를 가져다준다.
     * 내림차순인 이유는 ? 최신순으로 정렬해서 주기 위함.
     */
    List<Post> findByBoardTypeOrderByIdDesc(Pageable page, String boardType);

    /**
     * 마지막으로 받은 id 이후에 post들을 page 설정에 맞게 가져온다.
     */
    List<Post> findByBoardTypeAndIdLessThanOrderByIdDesc(Pageable page, String boardType, Long id);
    Boolean existsByIdLessThan(Long id);
}
