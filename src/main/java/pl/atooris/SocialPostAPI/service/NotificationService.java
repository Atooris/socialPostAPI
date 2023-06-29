package pl.atooris.SocialPostAPI.service;

import pl.atooris.SocialPostAPI.entity.Notification;
import pl.atooris.SocialPostAPI.entity.User;

import java.util.List;

public interface NotificationService {
    Notification saveNotification(Object associatedObject, User sender);
    void sendNotification(Notification notification);
    List<Notification> getNotifications(Long userId);
    List<Notification> readAndGetNotifications(Long userId);
}
