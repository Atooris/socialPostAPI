package pl.atooris.SocialPostAPI.service;

import pl.atooris.SocialPostAPI.entity.Hashtag;
import pl.atooris.SocialPostAPI.entity.Post;

import java.util.List;

public interface HashtagService {
    Hashtag getHashtag(Long id);
    List<Hashtag> getAllHashtags();
    List<Hashtag> getPostHashtags(Long postId);
    void associateHashtagsAndPost(Post post);
    List<String> extractHashtags(String content);
    void createNewHashtag(String name, Post post);
    void associateExistingHashtag(Hashtag existingHashtag, Post post);
}
