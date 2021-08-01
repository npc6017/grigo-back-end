package site.grigo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import site.grigo.domain.account.Account;
import site.grigo.domain.comment.Comment;
import site.grigo.domain.comment.CommentDTO;
import site.grigo.domain.post.CursorPage;
import site.grigo.domain.post.Post;
import site.grigo.domain.post.PostDTO;
import site.grigo.domain.post.PostRepository;
import site.grigo.domain.post.exception.PostAccountAndLoginAccountMisMatchException;
import site.grigo.domain.post.exception.PostNotFoundException;
import site.grigo.domain.posttag.PostTag;
import site.grigo.domain.posttag.PostTagRepository;
import site.grigo.domain.tag.Tag;
import site.grigo.domain.tag.TagRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final PostTagRepository postTagRepository;
    private final AccountService accountService;
    private final Integer contentLength = 35;

    //save에서 comment를 받을 필요는 없음. 처음 포스트하는데 댓글이 있을리 없기 때문에.

    /**
     * void -> Post
     */
    public Post savePost(PostDTO postDTO, String header) {
        Account account = accountService.getAccountToToken(header);
        // 받은 tag이름을 tag에서 가져오는 과정.
        List<Tag> tags = extractTags(postDTO.getTag());

        // post를 저장하는 과정.
        Post save = postRepository.save(postMapper(postDTO, account));

        // tag와 post를 postTag로 저장하는 과정.
        for (Tag tag : tags)
            postTagRepository.save(new PostTag(save, tag));

        return save;
    }

    private List<Tag> extractTags(List<String> tagsFromDto) {
        List<Tag> tags = new ArrayList<>();
        for (String tag : tagsFromDto) {
            Optional<Tag> byName = tagRepository.findByName(tag);
            tags.add(byName.get());
        }
        return tags;
    }

    // 존재하는지 확인한 후에 없으면 예외처리.
    public void deletePost(Long postId, String header) {
        Account account = accountService.getAccountToToken(header);
        Optional<Post> post = postRepository.findById(postId);

        // 로그인한 계정과 post의 작성자가 동일한지 확인. 아니면 exception 발생.
        if (!post.get().getAccount().equals(account)) throw new PostAccountAndLoginAccountMisMatchException();
        if (!post.isPresent()) throw new PostNotFoundException("delete");
        postRepository.delete(post.get());
    }

    // update할 때, tag가 바뀐다면? 어떻게 해줘야할까
    public void updatePost(Long postId, PostDTO postDTO, String header) {
        Account account = accountService.getAccountToToken(header);
        List<Tag> tags = extractTags(postDTO.getTag());
        Post post = postRepository.getById(postId);

        // 로그인한 계정과 post의 작성자가 동일한지 확인. 아니면 exception 발생.
        if (!post.getAccount().equals(account)) throw new PostAccountAndLoginAccountMisMatchException();

        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());

        // 애플리케이션 단에서 삭제되고 추가되는 데이터들을 받은 후에, 그에 맞게 업데이트하기.
        // -> 로직 변경. 현준&현배와 이야기하기.
        List<PostTag> tag = post.getTag();
        for (PostTag existTag : tag)
            postTagRepository.delete(existTag);
        for (Tag updateTag : tags)
            postTagRepository.save(new PostTag(post, updateTag));

        /** 08.01 수정  */
        postRepository.save(post);
    }

    //존재하는지 확인하고, 없으면 예외.
    public PostDTO servePost(Long postId, String header) {
        Account account = accountService.getAccountToToken(header);
        Optional<Post> byId = postRepository.findById(postId);
        //byId가 없으면, 예외 던지기
        if (byId.isEmpty()) throw new PostNotFoundException("select");
        return postDTOMapper(byId.get(), account);
    }

    /**
     * 해당되는 페이지에 대한 postDTO를 만들어주는 메소드.
     */
    public CursorPage get(Long id, Pageable page, String boardType) {
        List<PostDTO> postDTOS = new ArrayList<>();
        final List<Post> posts = getPosts(id, page, boardType);
        Long lastId = posts.isEmpty() ?
                null : posts.get(posts.size() - 1).getId();

        for (Post post : posts)
            postDTOS.add(postDTOMapper(post));

        postDTOS = contentSplit(postDTOS);
        CursorPage postDTOCursorPage = new CursorPage(postDTOS, hasNext(lastId, boardType));
        System.out.println(postDTOCursorPage.getPostDTOS().get(0).getContent());

        return postDTOCursorPage;
    }

    /**
     * 내용 원하는 길이만큼 잘라서 주기
     * 이것도 생각해보면 우리가 굳이 해줄 필요가 있나 생각이 들긴한다.
     * 서버에서 잘라주는 것보다는 각자의 핸드폰에서 자르는 게 더 효율적이기 때문에.
     * 이거에 대해서도 이야기해보기.
     * StringBuilder와 StringBuffer사이에서의 고민에서는 multi thread 하는 경우가 생길 거 같아서 Buffer 사용.
     */
    private List<PostDTO> contentSplit(List<PostDTO> postDTOS) {
        StringBuffer st;
        List<PostDTO> res = new ArrayList<>();
        for (PostDTO postDTO : postDTOS) {
            st = new StringBuffer();
            String content = postDTO.getContent();
            if (content.length() > contentLength) {
                st.append(content, 0, contentLength + 1);
                st.append("...");
            } else st.append(content);

            postDTO.setContent(st.toString());
            res.add(postDTO);
        }
        return res;
    }

    /**
     * 다음 페이지가 존재하는지에 대한 메소드
     */
    private Boolean hasNext(Long id, String boardType) {
        if (id == null) return false;
        return postRepository.existsByBoardTypeAndIdLessThan(boardType, id);
    }

    /**
     * DB에서 받았던 마지막 id의 이후 post들을 받아온다.
     * 만약 애플리케이션에서 첫번째 페이지를 입력받을 때는 id가 null로 올 것이므로,
     * null일 때는 가장 먼저 나와야하는 page를 return 해준다.
     * 그 후로는, 마지막으로 받은 id가 존재하기 때문에, page size에 맞춰서 해당하는 id 이후에 존재하는 post들을 return해준다.
     */
    public List<Post> getPosts(Long id, Pageable page, String boardType) {
        return id == null ?
                postRepository.findByBoardTypeOrderByIdDesc(page, boardType) :
                postRepository.findByBoardTypeAndIdLessThanOrderByIdDesc(page, boardType, id);
    }

    /**
     * 전체 게시판 post들을 가져올 때 쓰는 mapper
     * 굳이 comment를 받아올 필요가 없음.
     * 혹시 이견이 있다면 말해주길 부탁.
     */
    private PostDTO postDTOMapper(Post post) {
        List<String> tags = new ArrayList<>();

        // post에 속한 태그들 dto로 변환
        for (PostTag tag : post.getTag())
            tags.add(tag.getTag().getName());

        return new PostDTO(post.getId(), post.getTitle(), post.getAccount().getName(), post.getContent(), post.getBoardType(), tags, post.getTimeStamp());
    }

    /**
     *한 게시판에 post를 가져올 때 쓰는 Mapper
     * -> 차이는 account를 비교하여, 수정을 하냐 안하냐이다.
     */
    private PostDTO postDTOMapper(Post post, Account account) {
        PostDTO postDTO;
        List<String> tags = new ArrayList<>();
        List<CommentDTO> commentDTOS = new ArrayList<>();

        // post에 속한 태그들 dto로 변환
        for (PostTag tag : post.getTag())
            tags.add(tag.getTag().getName());

        // post에 속한 comment들 dto로 변환
        for (Comment comment : post.getComments())
            commentDTOS.add(commentDTOMapper(comment, account));

        postDTO = new PostDTO(post.getId(), post.getTitle(), post.getAccount().getName(), post.getContent(), post.getBoardType(), tags, commentDTOS, post.getTimeStamp());

        if (account.equals(post.getAccount()))
            postDTO.setUserCheck(true);
        return postDTO;
    }

    private CommentDTO commentDTOMapper(Comment comment, Account account) {
        CommentDTO commentDTO = new CommentDTO(comment.getId(), comment.getAccount().getName(),comment.getContent(), comment.getTimeStamp());

        if(comment.getAccount().equals(account)) commentDTO.setUserCheck(true);
        return commentDTO;
    }

    private Post postMapper(PostDTO postDTO, Account account) {
        return new Post(postDTO.getTitle(), account, postDTO.getContent(), postDTO.getBoardType());
    }
}
