package pl.atooris.SocialPostAPI.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.atooris.SocialPostAPI.entity.Like;
import pl.atooris.SocialPostAPI.entity.Post;
import pl.atooris.SocialPostAPI.entity.User;
import pl.atooris.SocialPostAPI.repository.LikeRepository;
import pl.atooris.SocialPostAPI.repository.PostRepository;
import pl.atooris.SocialPostAPI.repository.UserRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LikeServiceImpl implements LikeService{

    LikeRepository likeRepository;
    NotificationService notificationService;
    PostService postService;
    UserService userService;

    @Override
    public void addOrDeleteLikeFromPost(Like like, Long postId, Long userId) {
        User user = userService.getUser(userId);
        Post post = postService.getPost(postId);

        like.setAuthor(user);
        like.setPost(post);

        if (isLikeExist(post, like)) {
            deleteLikeFromPost(postId, userId);
        }
        else {
            post.getLikes().add(like);
//            createLikeNotification(like);
            likeRepository.save(like);
        }
    }

    private void createLikeNotification(Like like) {
        if (like.getNotificationCooldown().isBefore(Instant.now())) {
            notificationService.createLikeNotification(like);
            like.setNotificationCooldown(Instant.now().plusSeconds(7200));  // 2 hours
        }
    }

    @Override
    public void deleteLikeFromPost(Long postId, Long authorId) {
        likeRepository.deleteLikeByPostIdAndAuthorId(postId, authorId);
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
