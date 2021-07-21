package site.grigo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import site.grigo.domain.post.PostDTO;
import site.grigo.service.PostService;

@RequestMapping("/posts")
@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/free")
    public String freeBoard() {
        return "ok";
    }

    @GetMapping("/question")
    public String questionBoard() {
        return "ok";
    }

    @PostMapping("/save")
    public String savePost(@RequestBody PostDTO postDTO) {
        postService.savePost(postDTO);
        return "ok save";
    }
}
