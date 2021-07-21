package site.grigo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import site.grigo.domain.account.Account;
import site.grigo.domain.comment.Comment;
import site.grigo.domain.comment.CommentDTO;
import site.grigo.domain.comment.CommentRepository;
import site.grigo.domain.post.Post;
import site.grigo.domain.post.PostRepository;
import site.grigo.service.AccountService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class CommentController {

    private final AccountService accountService;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    /** Comment Add */
    @PostMapping("/{postId}/comment")
    public ResponseEntity setComment(@PathVariable Long postId, HttpServletRequest request, @RequestBody CommentDTO comment) {
        // ToDo Get User
        Account account = getAccount(request);

        // ToDo Check isPost
        Optional<Post> post = postRepository.findById(postId);
        if(post.isEmpty())
            return new ResponseEntity<>("게시글이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);

        // ToDo Insert Comment
        Comment newComment = new Comment();
        newComment.setContent(comment.getContent());
        newComment.setAccount(account);
        newComment.setPost(post.get());
        commentRepository.save(newComment);

        return new ResponseEntity<>("댓글을 성공적으로 작성하였습니다.", HttpStatus.OK);
    }

    /** Comment Update */
    @PostMapping("/comment/change/{commentId}")
    public ResponseEntity updateComment(@PathVariable Long commentId,@RequestBody CommentDTO commentDTO ,HttpServletRequest request) {
        // ToDo Get Account
        Account account = getAccount(request);

        // ToDo Check IsComment And IsOwner
        Optional<Comment> comment = commentRepository.findById(commentId);
        if(comment.isEmpty()) // Check IsComment
            return new ResponseEntity("존재하지 않는 댓글입니다.", HttpStatus.BAD_REQUEST);
        if(!comment.get().getAccount().equals(account)) // Check IsOwner
            return new ResponseEntity("잘못된 접근입니다.", HttpStatus.BAD_REQUEST);

        // ToDo Update Comment
        comment.get().setContent(commentDTO.getContent());
        commentRepository.save(comment.get());
        return new ResponseEntity("댓글이 성공적으로 수정되었습니다.", HttpStatus.OK);
    }


    /** Comment Delete */
    @PostMapping("/comment/{commentId}")
    public ResponseEntity deleteComment(@PathVariable Long commentId, HttpServletRequest request) {
        // ToDo Get User
        Account account = getAccount(request);

        // ToDo Check IsComment And IsOwner
        Optional<Comment> comment = commentRepository.findById(commentId);
        if(comment.isEmpty()) // Check IsComment
            return new ResponseEntity("존재하지 않는 댓글입니다.", HttpStatus.BAD_REQUEST);
        if(!comment.get().getAccount().equals(account)) // Check IsOwner
            return new ResponseEntity("잘못된 접근입니다.", HttpStatus.BAD_REQUEST);

        // ToDo Delete Comment
        commentRepository.delete(comment.get());
        return new ResponseEntity("댓글이 정상적으로 삭제되었습니다.", HttpStatus.OK);
    }

    /* 메서드 분리 - Get Account*/
    private Account getAccount(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return accountService.getAccountToToken(token);
    }
}
