package pl.atooris.SocialPostAPI.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.atooris.SocialPostAPI.entity.*;
import pl.atooris.SocialPostAPI.repository.NotificationRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService{

    NotificationRepository notificationRepository;

    @Override
    public void sendNotification(Notification notification) {
        notification.getReceivers().forEach(user -> user.getNotifications().add(notification));
    }

    @Override
    public Notification saveNotification(Object associatedObject, User sender) {
        Notification notification = new Notification();
        notification.setSender(sender);
        if(associatedObject instanceof Post){
            sender.getFollowers().forEach(follow -> notification.getReceivers().add(follow));
            notification.setContent(sender.getUsername() + " added a new post! Check it out!");
        }
        else if(associatedObject instanceof Like){
            User receiver = ((Like) associatedObject).getPost().getAuthor();
            notification.getReceivers().add(receiver);
            notification.setContent(sender.getUsername() + " likes your post!");
        }
        else if(associatedObject instanceof Comment){
            User receiver = ((Comment) associatedObject).getPost().getAuthor();
            notification.getReceivers().add(receiver);
            notification.setContent(sender.getUsername() + " commented your post!\n \"" + ((Comment) associatedObject).getContent() + "\"");
        }
        sendNotification(notification);
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
