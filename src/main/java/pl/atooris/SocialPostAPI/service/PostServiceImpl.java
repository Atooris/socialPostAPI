package pl.atooris.SocialPostAPI.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.atooris.SocialPostAPI.entity.Post;
import pl.atooris.SocialPostAPI.entity.User;
import pl.atooris.SocialPostAPI.exception.EntityNotFoundException;
import pl.atooris.SocialPostAPI.repository.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class PostServiceImpl implements PostService{

    PostRepository postRepository;
    UserRepository userRepository;
    HashtagService hashtagService;
    RoleRepository roleRepository;
    NotificationService notificationService;

    @Override
    public Post getPost(Long id) {
        Optional<Post> post = postRepository.findById(id);
        return unwrapPost(post, id);
    }

    @Override
    public Post savePost(Post post, Long authorId) {
        Optional<User> author = userRepository.findById(authorId);
        User unwrappedAuthor = UserServiceImpl.unwrapUser(author, authorId);

        post.setAuthor(unwrappedAuthor);
        postRepository.save(post);
        hashtagService.associateHashtagsAndPost(post);
        notificationService.createPostNotification(post);
        return post;
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deletePostById(id);
    }

    @Override
    public Post updatePost(String content, Long id) {
        Optional<Post> post = postRepository.findById(id);
        Post unwrappedPost = unwrapPost(post, id);

        unwrappedPost.setContent(content);
        unwrappedPost.getHashtags().clear();
        hashtagService.associateHashtagsAndPost(unwrappedPost);
        return postRepository.save(unwrappedPost);
    }

    @Override
    public List<Post> getUserPosts(Long userId) {
        return postRepository.findByAuthorId(userId);
    }

    @Override
    public boolean isOwnerOfPost(Long postId, Long userId) {
        return getPost(postId).getAuthor().getId() == userId;
    }

    static Post unwrapPost(Optional<Post> entity, Long id){
        if(entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(id, Post.class);
    }

}
