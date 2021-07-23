package site.grigo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.grigo.domain.account.Account;
import site.grigo.domain.post.Post;
import site.grigo.domain.post.PostDTO;
import site.grigo.domain.post.PostRepository;
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

    public void savePost(PostDTO postDTO, String header) {
        Account account = accountService.getAccountToToken(header);
        // 받은 tag이름을 tag에서 가져오는 과정.
        List<Tag> tags = extractTags(postDTO.getTag());

        // post를 저장하는 과정.
        Post save = postRepository.save(postMapper(postDTO, account));

        // tag와 post를 postTag로 저장하는 과정.
        for(Tag tag : tags)
            postTagRepository.save(new PostTag(save, tag));
    }

    private List<Tag> extractTags(List<String> tagsFromDto) {
        List<Tag> tags = new ArrayList<>();
        for(String tag : tagsFromDto) {
            Optional<Tag> byName = tagRepository.findByName(tag);
            tags.add(byName.get());
        }
        return tags;
    }

    //존재하는지 확인한 후에 없으면 예외처리.
    public void deletePost(Long postId) {
        Optional<Post> post = postRepository.findById(postId);
        postRepository.delete(post.get());
        postRepository.flush();
    }

    //update할 때, tag가 바뀐다면? 어떻게 해줘야할까
    //
    public void updatePost(Long postId, PostDTO postDTO) {
        List<Tag> tags = extractTags(postDTO.getTag());
        Post post = postRepository.getById(postId);
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());


        // 이 로직에 단점.
        // 업데이트 처리가 나면 무조건 삭제하고, 다시 넣는다는 점. == 데이터베이스에 쓸모없는 작동을 야기함.
        // 해결책. tag가 변경되는지 확인하는 인자가 필요.
        // 해결책2 : 방식을 변경. -> postTag에서 존재하는 친구들을 찾아서, 삭제해주거나 추가해줌. == 로직이 더러움.
        // 무엇을 선택 ?
        // 삭제할 tag와 추가할 tag를 list로 결정해서 한다.
        List<PostTag> tag = post.getTag();
        for(PostTag existTag : tag)
            postTagRepository.delete(existTag);
        for(Tag updateTag : tags)
            postTagRepository.save(new PostTag(post, updateTag));

        postTagRepository.flush();
        postRepository.flush();
    }

    //존재하는지 확인하고, 없으면 예외.
    public PostDTO serverPost(Long postId) {
        Post post = postRepository.findById(postId).get();
        //byId가 없으면, 예외 던지기
        return postDTOMapper(post);
    }

    public List<PostDTO> getAllPosts(String type) {
        List<PostDTO> res = new ArrayList<>();
        List<Post> allPosts = postRepository.findAllByBoardType(type);
        if(type.equals("free")) {
            for(Post post : allPosts) {
                PostDTO postDTO = postDTOMapper(post);
                if(postDTO.getTag().isEmpty()) res.add(postDTO);
            }
            return res;
        }
        for(Post post : allPosts) {
            PostDTO postDTO = postDTOMapper(post);
            if(!postDTO.getTag().isEmpty()) res.add(postDTO);
        }
        return res;
    }

    private PostDTO postDTOMapper(Post post) {
        List<String> tags = new ArrayList<>();
        for(PostTag tag : post.getTag())
            tags.add(tag.getTag().getName());

        return new PostDTO(post.getId(), post.getTitle(), post.getAccount().getName(), post.getContent(), post.getBoardType(), tags, post.getTimeStamp());
    }

    private Post postMapper(PostDTO postDTO, Account account) {
        return new Post(postDTO.getTitle(), account, postDTO.getContent(), postDTO.getBoardType());
    }
}
