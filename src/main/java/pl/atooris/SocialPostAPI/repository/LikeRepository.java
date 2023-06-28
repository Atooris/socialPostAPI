package pl.atooris.SocialPostAPI.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import pl.atooris.SocialPostAPI.entity.Like;

import java.util.List;

public interface LikeRepository extends CrudRepository<Like, Long> {
    @Transactional
    void deleteByPostIdAndAuthorId(Long postId, Long userId);
    List<Like> findByAuthorId(Long userId);
    List<Like> findByPostId(Long postId);
    Like findByAuthorIdAndPostId(Long userId, Long postId);
}
