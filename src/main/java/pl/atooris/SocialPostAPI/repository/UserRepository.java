package pl.atooris.SocialPostAPI.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import pl.atooris.SocialPostAPI.entity.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    @Transactional
    void deleteUserById(Long id);
    Optional<User> findByUsername(String username);
}
