package pl.atooris.SocialPostAPI.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.atooris.SocialPostAPI.entity.Comment;
import pl.atooris.SocialPostAPI.service.CommentService;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("user/{userId}/post/{postId}")
public class CommentController {

    CommentService commentService;

    @GetMapping("/comment/{id}")
    public ResponseEntity<Comment> getComment(@PathVariable Long id){
        return new ResponseEntity<>(commentService.getComment(id), HttpStatus.OK);
    }

    @PostMapping("/comment")
    public ResponseEntity<Comment> saveComment(@Valid @RequestBody Comment comment, @PathVariable Long userId, @PathVariable Long postId){
        return new ResponseEntity<>(commentService.saveComment(comment, postId, userId), HttpStatus.CREATED);
    }

    @PutMapping("/comment/{id}")
    public ResponseEntity<Comment> updateComment(@Valid @RequestBody Comment comment, @PathVariable Long id){
        return new ResponseEntity<>(commentService.updateComment(comment.getContent(), id), HttpStatus.OK);
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable Long id){
        commentService.deleteComment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/post-comments")
    public ResponseEntity<List<Comment>> getPostComments(@PathVariable Long postId){
        return new ResponseEntity<>(commentService.getPostComments(postId), HttpStatus.OK);
    }

    @GetMapping("/user-comments")
    public ResponseEntity<List<Comment>> getUserComments(@PathVariable Long userId){
        return new ResponseEntity<>(commentService.getUserComments(userId), HttpStatus.OK);
    }
}
