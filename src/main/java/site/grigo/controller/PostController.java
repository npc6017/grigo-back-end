package site.grigo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.grigo.domain.post.CursorPage;
import site.grigo.domain.post.Post;
import site.grigo.domain.post.PostDTO;
import site.grigo.service.AccountService;
import site.grigo.service.PostService;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/posts")
@RestController
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private static final int DEFAULT_SIZE = 10;
    private final PostService postService;
    private final AccountService accountService;

    /**
     * 시작하는 postId와 한 페이지당 들어가야할 size를 입력받는다.
     * 마지막으로 받은 id와 size를 입력받는다.
     * pogeRequest는 페이지를 만들어주는 역할을 한다.
     * pogeRequest.of(0, size)의 의미는 0번부터 페이지를 만들어서 넘긴다는 의미이다.
     */
    @GetMapping("/free")
    public CursorPage freeBoard(@RequestParam(value = "id") Long id, @RequestParam(value = "size") Integer size){
        System.out.println(id + "  " + size);
        if(size == null) size = DEFAULT_SIZE;
        return postService.get(id, PageRequest.of(0, size), "free");
    }

    @GetMapping("/question")
    public CursorPage questionBoard(@RequestParam(value = "id") Long id, @RequestParam(value = "size") Integer size) {
        System.out.println(id + "  " + size);
        if(size == null) size = DEFAULT_SIZE;
        return postService.get(id, PageRequest.of(0, size), "question");
    }

    // post 선택했을 때, 가져오는 메소드.
    @GetMapping("/{postId}")
    public PostDTO selectPost(@PathVariable Long postId) {
        return postService.servePost(postId);
    }

    //ResponseEntity로 결과 알려주기.
    /**
     * 토큰 token으로 빼내어 재사용. */
    @PostMapping("/save")
    public ResponseEntity savePost(HttpServletRequest request, @RequestBody PostDTO postDTO) {
        /* 게시글 생성 */
        String token = request.getHeader("Authorization");
        Post post = postService.savePost(postDTO, token);

        /** 태그가 있는 경우 알림 생성 */
        if(!postDTO.getTag().isEmpty())
            accountService.setNotification(post, postDTO);

        return new ResponseEntity("post save successful", HttpStatus.OK);
    }

    //ResponseEntity로 결과 알려주기.
    @PostMapping("/{postId}/delete")
    public ResponseEntity deletePost(HttpServletRequest request, @PathVariable Long postId) {
        postService.deletePost(postId, request.getHeader("Authorization"));
        return new ResponseEntity("post delete successful", HttpStatus.OK);
    }

    //ResponseEntity로 결과 알려주기.
    @PostMapping("/{postId}/update")
    public ResponseEntity updatePost(HttpServletRequest request, @PathVariable Long postId, @RequestBody PostDTO postDTO) {
        postService.updatePost(postId, postDTO, request.getHeader("Authorization"));
        return new ResponseEntity("post update successful", HttpStatus.OK);
    }
}
