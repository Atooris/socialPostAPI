package pl.atooris.SocialPostAPI.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.atooris.SocialPostAPI.entity.Like;
import pl.atooris.SocialPostAPI.entity.Post;
import pl.atooris.SocialPostAPI.entity.User;
import pl.atooris.SocialPostAPI.repository.LikeRepository;
import pl.atooris.SocialPostAPI.repository.PostRepository;
import pl.atooris.SocialPostAPI.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LikeServiceImpl implements LikeService{

    LikeRepository likeRepository;
    PostRepository postRepository;
    UserRepository userRepository;

    @Override
    public void addOrDeleteLikeFromPost(Like like, Long postId, Long userId) {
        Optional<Post> post = postRepository.findById(postId);
        Post unwrappedPost = PostServiceImpl.unwrapPost(post, postId);
        Optional<User> user = userRepository.findById(userId);
        User unwrappedUser = UserServiceImpl.unwrapUser(user, userId);

        like.setAuthor(unwrappedUser);
        like.setPost(unwrappedPost);

        if (isLikeExist(unwrappedPost, like)) {
            deleteLikeFromPost(postId, userId);
        } else {
            unwrappedPost.getLikes().add(like);
            likeRepository.save(like);
        }
    }

    @Override
    public void deleteLikeFromPost(Long postId, Long userId) {
        likeRepository.deleteByPostIdAndAuthorId(postId, userId);
    }

    @Override
    public List<Like> getPostLikes(Long postId) {
        return likeRepository.findByPostId(postId);
    }

    @Override
    public List<Like> getUserLikes(Long userId) {
        return likeRepository.findByAuthorId(userId);
    }

    @Override
    public Integer getPostLikesCount(Long postId) {
        return likeRepository.findByPostId(postId).size();
    }

    static boolean isLikeExist(Post post, Like like){
        return post.getLikes().stream()
                .anyMatch(x -> like.getAuthor().getId().equals(x.getAuthor().getId())
                        && like.getPost().getId().equals(x.getPost().getId()));
    }

}
