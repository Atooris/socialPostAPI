package pl.atooris.SocialPostAPI.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import pl.atooris.SocialPostAPI.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    Optional<Comment> findByPostIdAndAuthorId(Long postId, Long authorId);
    @Transactional
    void deleteByPostIdAndAuthorId(Long postId, Long authorId);
    List<Comment> findByPostId(Long postId);
    List<Comment> findByAuthorId(Long userId);
}
