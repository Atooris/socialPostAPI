package pl.atooris.SocialPostAPI.service;

import pl.atooris.SocialPostAPI.entity.Comment;
import pl.atooris.SocialPostAPI.entity.Post;

import java.util.List;

public interface PostService {
    Post getPost(Long id);
    Post savePost(Post post, Long authorId);
    void deletePost(Long id);
    Post updatePost(String content, Long id);
    List<Post> getUserPosts(Long userId);
//    void hasAccessToResource(Long postId, Long userId);
    boolean isOwnerOfPost(Long postId, Long userId);
}
