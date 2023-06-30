package pl.atooris.SocialPostAPI.service;

import pl.atooris.SocialPostAPI.entity.*;

import java.util.List;

public interface NotificationService {
    public Notification createPostNotification(Post trigger);
    public Notification createFollowNotification(User trigger, User receiver);
    public Notification createLikeNotification(Like trigger);
    public Notification createCommentNotification(Comment trigger);
    public Notification saveAndSendNotificationToReceivers(Notification notification);
    void sendNotificationToReceivers(Notification notification);
    List<Notification> getNotifications(Long userId);
    List<Notification> readAndGetNotifications(Long userId);
}
