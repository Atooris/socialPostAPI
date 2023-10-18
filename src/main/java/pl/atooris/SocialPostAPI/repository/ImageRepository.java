package pl.atooris.SocialPostAPI.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import pl.atooris.SocialPostAPI.entity.Image;

import java.util.List;

public interface ImageRepository extends CrudRepository<Image, Long> {
    @Transactional
    void deleteById(Long id);
    List<Image> findByAuthorId(Long userId);
    List<Image> findByPostId(Long postId);
}
