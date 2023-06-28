package pl.atooris.SocialPostAPI.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.atooris.SocialPostAPI.entity.Hashtag;
import pl.atooris.SocialPostAPI.entity.Post;
import pl.atooris.SocialPostAPI.exception.EntityNotFoundException;
import pl.atooris.SocialPostAPI.repository.HashtagRepository;
import pl.atooris.SocialPostAPI.repository.PostRepository;

import java.util.*;

@Service
@AllArgsConstructor
public class HashtagServiceImpl implements HashtagService{

    HashtagRepository hashtagRepository;
    PostRepository postRepository;

    @Override
    public Hashtag getHashtag(Long id) {
        Optional<Hashtag> hashtag = hashtagRepository.findById(id);
        return unwrapHashtag(hashtag, id);
    }

    @Override
    public List<Hashtag> getAllHashtags() {
        return (List<Hashtag>) hashtagRepository.findAll();
    }

    @Override
    public List<Hashtag> getPostHashtags(Long postId) {
        return hashtagRepository.findByPostsId(postId);
    }

    public void associateHashtagsAndPost(Post post){
        List<String> hashtagsNames = extractHashtags(post.getContent());
        if(post.getHashtags() == null) post.setHashtags(new HashSet<>());

        for(String name : hashtagsNames){
            Optional<Hashtag> hashtag = hashtagRepository.findByName(name);
            if(hashtag.isEmpty()){
                createNewHashtag(name, post);
            } else {
                associateExistingHashtag(hashtag.get(), post);
            }
        }
    }

    public List<String> extractHashtags(String content){
        String[] words = content.split(" ");
        return Arrays.stream(words).filter(hashtag -> hashtag.startsWith("#")).toList();
    }

    public void createNewHashtag(String name, Post post){
        Hashtag newHashtag = new Hashtag(name);
        if(newHashtag.getPosts() == null) newHashtag.setPosts(new HashSet<>());
        newHashtag.getPosts().add(post);
        post.getHashtags().add(newHashtag);
        hashtagRepository.save(newHashtag);
    }

    public void associateExistingHashtag(Hashtag existingHashtag, Post post){
        existingHashtag.getPosts().add(post);
        post.getHashtags().add(existingHashtag);
    }

    static Hashtag unwrapHashtag(Optional<Hashtag> entity, Long id){
        if(entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(id, Hashtag.class);
    }
}
