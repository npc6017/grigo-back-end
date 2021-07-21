package site.grigo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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

    public void savePost(PostDTO postDTO) {
        List<Tag> tags = new ArrayList<>();
        // 받은 tag이름을 tag에서 가져오는 과정.
        List<String> tagsFromDto = postDTO.getTag();
        log.info("{}", tagsFromDto);
        for(String tag : tagsFromDto) {
            Optional<Tag> byName = tagRepository.findByName(tag);
            tags.add(byName.get());
            log.info("{}", byName.get().getName());
        }
        // post를 저장하는 과정.
        Post save = postRepository.save(postMapper(postDTO));

        // tag와 post를 postTag로 저장하는 과정.
        for(Tag tag : tags)
            postTagRepository.save(new PostTag(save, tag));
    }

    public List<PostDTO> getAllPosts(String type) {
        List<PostDTO> res = new ArrayList<>();
        List<Post> all = postRepository.findAll();
        if(type.equals("free")) {
            for(Post post : all) {
                PostDTO postDTO = postDTOMapper(post);
                if(postDTO.getTag().isEmpty()) res.add(postDTO);
            }
            return res;
        }
        for(Post post : all) {
            PostDTO postDTO = postDTOMapper(post);
            if(!postDTO.getTag().isEmpty()) res.add(postDTO);
        }
        return res;
    }

    private PostDTO postDTOMapper(Post post) {
        List<String> tags = new ArrayList<>();
        for(PostTag tag : post.getTag())
            tags.add(tag.getTag().getName());

        return new PostDTO(post.getId(), post.getTitle(), post.getWriter(), post.getContent(), tags, post.getTimeStamp());
    }

    private Post postMapper(PostDTO postDTO) {
        return new Post(postDTO.getTitle(), postDTO.getWriter(), postDTO.getContent());
    }
}
