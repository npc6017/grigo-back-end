package site.grigo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.web.bind.annotation.*;
import site.grigo.domain.post.PostDTO;
import site.grigo.service.PostService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@RequestMapping("/posts")
@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/free")
    @ResponseBody
    public HashMap<String, List<PostDTO>> freeBoard(){
        List<PostDTO> free = postService.getAllPosts("free");
        HashMap<String, List<PostDTO>> res = new HashMap<>();
        res.put("post", free);
        return res;
    }

    @GetMapping("/question")
    public HashMap<String, List<PostDTO>> questionBoard() {
        List<PostDTO> question = postService.getAllPosts("question");
        HashMap<String, List<PostDTO>> res = new HashMap<>();
        res.put("post", question);
        return res;
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
