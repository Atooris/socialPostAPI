package pl.atooris.SocialPostAPI.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import pl.atooris.SocialPostAPI.entity.Post;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {
    @Transactional
    void deletePostById(Long id);
    List<Post> findByAuthorId(Long userId);
}
