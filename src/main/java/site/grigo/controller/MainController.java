package site.grigo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import site.grigo.domain.post.Post;
import site.grigo.domain.post.PostRepository;

@RestController
@RequestMapping("/")
@Slf4j
public class MainController {

    private final PostRepository postRepository;

    public MainController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @ResponseBody
    @GetMapping("/home")
    public String home(){
        return "ok";
    }

    @ResponseBody
    @PostMapping("/home/test")
    public Post test(@RequestBody Post post) {
        log.info("{}, {}, {}", post.getContent(), post.getTitle(), post.getWriter());
        Post save = postRepository.save(post);
        return save;
    }
}
