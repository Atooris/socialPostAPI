package pl.atooris.SocialPostAPI.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.atooris.SocialPostAPI.entity.Like;
import pl.atooris.SocialPostAPI.service.LikeService;

import java.util.List;

@RestController
@RequestMapping("/user/{userId}/post/{postId}")
public class LikeController {

    @Autowired
    LikeService likeService;

    @PutMapping("/like")
    public ResponseEntity<HttpStatus> addLike(@Valid @RequestBody Like like, @PathVariable Long userId, @PathVariable Long postId){
        likeService.addOrDeleteLikeFromPost(like, postId, userId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/like")
    public ResponseEntity<HttpStatus> deleteLike(@PathVariable Long postId, @PathVariable Long userId){
        likeService.deleteLikeFromPost(postId, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/post-likes")
    public ResponseEntity<List<Like>> getPostLikes(@PathVariable Long postId){
        return new ResponseEntity<>(likeService.getPostLikes(postId), HttpStatus.OK);
    }

    @GetMapping("/user-likes")
    public ResponseEntity<List<Like>> getUserLikes(@PathVariable Long userId){
        return new ResponseEntity<>(likeService.getUserLikes(userId), HttpStatus.OK);
    }

    @GetMapping("/like-count")
    public ResponseEntity<Integer> getPostLikeCount(@PathVariable Long postId){
        return new ResponseEntity<>(likeService.getPostLikesCount(postId), HttpStatus.OK);
    }
}
