package site.grigo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import site.grigo.domain.post.PostDTO;
import site.grigo.service.PostService;

import java.util.List;

@RequestMapping("/posts")
@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/free")
    public List<PostDTO> freeBoard() {
        List<PostDTO> free = postService.getAllPosts("free");
        return free;
    }

    @GetMapping("/question")
    public List<PostDTO> questionBoard() {
        List<PostDTO> question = postService.getAllPosts("question");
        return question;
    }

    // post 선택했을 때, 가져오는 메소드.
    @GetMapping("/{postId}")
    public PostDTO selectPost(@PathVariable Long postId) {
        return postService.serverPost(postId);
    }

    @PostMapping("/save")
    public String savePost(@RequestBody PostDTO postDTO) {
        postService.savePost(postDTO);
        return "ok save";
    }

    @PostMapping("/{postId}/delete")
    public String deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return "delete ok";
    }

    @PostMapping("/{postId}/update")
    public String updatePost(@PathVariable Long postId, @RequestBody PostDTO postDTO) {
        postService.updatePost(postId, postDTO);
        return "update ok";
    }
}
