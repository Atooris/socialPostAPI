package pl.atooris.SocialPostAPI.repository;

import org.springframework.data.repository.CrudRepository;
import pl.atooris.SocialPostAPI.entity.Notification;

import java.util.List;

public interface NotificationRepository extends CrudRepository<Notification, Long> {
    List<Notification> findByReceiversId(Long receiverId);
}
