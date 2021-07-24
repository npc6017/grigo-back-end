package site.grigo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import site.grigo.domain.post.CursorPage;
import site.grigo.domain.post.PostDTO;
import site.grigo.service.PostService;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/posts")
@RestController
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private static final int DEFAULT_SIZE = 10;
    private final PostService postService;

    /**
     * 시작하는 postId와 한 페이지당 들어가야할 size를 입력받는다.
     * 마지막으로 받은 id와 size를 입력받는다.
     * pogeRequest는 페이지를 만들어주는 역할을 한다.
     * pogeRequest.of(0, size)의 의미는 0번부터 페이지를 만들어서 넘긴다는 의미이다.
     */
    @GetMapping("/free")
    public CursorPage<PostDTO> freeBoard(Long id, Integer size){
        if(size == null) size = DEFAULT_SIZE;
        return postService.get(id, PageRequest.of(0, size), "free");
    }

    @GetMapping("/question")
    public CursorPage<PostDTO> questionBoard(Long id, Integer size) {
        if(size == null) size = DEFAULT_SIZE;
        return postService.get(id, PageRequest.of(0, size), "question");
    }

    // post 선택했을 때, 가져오는 메소드.
    @GetMapping("/{postId}")
    public PostDTO selectPost(@PathVariable Long postId) {
        return postService.serverPost(postId);
    }

    @PostMapping("/save")
    public String savePost(HttpServletRequest request, @RequestBody PostDTO postDTO) {
        postService.savePost(postDTO, request.getHeader("Authorization"));
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
