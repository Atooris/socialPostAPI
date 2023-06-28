package pl.atooris.SocialPostAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.atooris.SocialPostAPI.entity.Hashtag;
import pl.atooris.SocialPostAPI.service.HashtagService;

import java.util.List;

@RestController
public class HashtagController {

    @Autowired
    HashtagService hashtagService;

    @GetMapping("/hashtag/{id}")
    public ResponseEntity<Hashtag> getHashtag(@PathVariable Long id){
        return new ResponseEntity<>(hashtagService.getHashtag(id), HttpStatus.OK);
    }

    @GetMapping("/hashtags")
    public ResponseEntity<List<Hashtag>> getAllHashtags(){
        return new ResponseEntity<>(hashtagService.getAllHashtags(), HttpStatus.OK);
    }

    @GetMapping("/post/{postId}/hashtags")
    public ResponseEntity<List<Hashtag>> getPostHashtags(@PathVariable Long postId){
        return new ResponseEntity<>(hashtagService.getPostHashtags(postId), HttpStatus.OK);
    }
}
