package pl.atooris.SocialPostAPI.service;

import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Service;
import pl.atooris.SocialPostAPI.entity.*;
import pl.atooris.SocialPostAPI.repository.NotificationRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService{

    NotificationRepository notificationRepository;

    @Override
    public void sendNotificationToReceivers(Notification notification) {
        notification.getReceivers().forEach(user -> user.getNotifications().add(notification));
    }

//    @Override
//    public Notification saveNotification(Object triggerObject, User sender) {
//        Notification notification = new Notification();
//        notification.setSender(sender);
//        if(triggerObject instanceof Post){
//            sender.getFollowers().forEach(follow -> notification.getReceivers().add(follow));
//            notification.setContent(sender.getUsername() + " added a new post! Check it out!");
//        }
//        else if(triggerObject instanceof Like){
//            User receiver = ((Like) triggerObject).getPost().getAuthor();
//            notification.getReceivers().add(receiver);
//            notification.setContent(sender.getUsername() + " likes your post!");
//        }
//        else if(triggerObject instanceof Comment){
//            User receiver = ((Comment) triggerObject).getPost().getAuthor();
//            notification.getReceivers().add(receiver);
//            notification.setContent(sender.getUsername() + " commented your post!\n \"" + ((Comment) triggerObject).getContent() + "\"");
//        }
//        sendNotificationToReceivers(notification);
//        return notificationRepository.save(notification);
//    }
    @Override
    public Notification createPostNotification(Post trigger){
        Notification notification = new Notification();
        User sender = trigger.getAuthor();
        notification.setSender(sender);
        sender.getFollowers().forEach(follow -> notification.getReceivers().add(follow));
        notification.setContent(sender.getUsername() + " added a new post! Check it out!");
        return saveAndSendNotificationToReceivers(notification);
    }

    @Override
    public Notification createFollowNotification(User trigger, User receiver){
        Notification notification = new Notification();
        notification.setSender(trigger);
        notification.getReceivers().add(receiver);
        notification.setContent(trigger.getUsername() + " follows you!");
        return saveAndSendNotificationToReceivers(notification);
    }

    @Override
    public Notification createLikeNotification(Like trigger){
        Notification notification = new Notification();
        User sender = trigger.getAuthor();
        User receiver = trigger.getPost().getAuthor();
        notification.setSender(sender);
        notification.getReceivers().add(receiver);
        notification.setContent(sender.getUsername() + " likes your post!");
        return saveAndSendNotificationToReceivers(notification);
    }

    @Override
    public Notification createCommentNotification(Comment trigger){
        Notification notification = new Notification();
        User sender = trigger.getAuthor();
        User receiver = trigger.getPost().getAuthor();
        notification.setSender(sender);
        notification.getReceivers().add(receiver);
        notification.setContent(sender.getUsername() + " commented your post!\n \"" + trigger.getContent() + "\"");
        return saveAndSendNotificationToReceivers(notification);
    }

    @Override
    public Notification saveAndSendNotificationToReceivers(Notification notification){
        sendNotificationToReceivers(notification);
        return notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getNotifications(Long userId) {
        return notificationRepository.findByReceiversId(userId);
    }

    @Override
    public List<Notification> readAndGetNotifications(Long userId) {
        notificationRepository.findByReceiversId(userId)
                .forEach(notification -> notification.setRead(true));
        return getNotifications(userId);
    }
}
