package pl.atooris.SocialPostAPI.controller;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.atooris.SocialPostAPI.entity.Post;
import pl.atooris.SocialPostAPI.service.PostService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/user/{userId}")
public class PostController {

    PostService postService;

    @GetMapping("/post/{id}")
    public ResponseEntity<Post> getPost(@PathVariable Long id){
        return new ResponseEntity<>(postService.getPost(id), HttpStatus.OK);
    }

    @PostMapping("/post")
    public ResponseEntity<Post> savePost(@Valid @RequestBody Post post, @PathVariable Long userId){
        return new ResponseEntity<>(postService.savePost(post, userId), HttpStatus.CREATED);
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity<HttpStatus> deletePost(@PathVariable Long id, @PathVariable Long userId){
//        postService.hasAccessToResource(id, userId);
        postService.deletePost(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/post/{id}")
    public ResponseEntity<Post> updatePost(@Valid @RequestBody Post post, @PathVariable Long id){
        return new ResponseEntity<>(postService.updatePost(post.getContent(), id), HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getUserPosts(@PathVariable Long userId){
        return new ResponseEntity<>(postService.getUserPosts(userId), HttpStatus.OK);
    }



}
