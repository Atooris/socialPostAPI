package pl.atooris.SocialPostAPI.repository;

import org.springframework.data.repository.CrudRepository;
import pl.atooris.SocialPostAPI.entity.Hashtag;

import java.util.List;
import java.util.Optional;

public interface HashtagRepository extends CrudRepository<Hashtag, Long> {
    Optional<Hashtag> findByName(String name);
    List<Hashtag> findByPostsId(Long postId);
}
