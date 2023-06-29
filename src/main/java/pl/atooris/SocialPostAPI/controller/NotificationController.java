package pl.atooris.SocialPostAPI.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.atooris.SocialPostAPI.entity.Notification;
import pl.atooris.SocialPostAPI.service.NotificationService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/user/{userId}")
public class NotificationController {
    NotificationService notificationService;

    @GetMapping("/notifications")
    public ResponseEntity<List<Notification>> getNotifications(@PathVariable Long userId){
        return new ResponseEntity<>(notificationService.readAndGetNotifications(userId), HttpStatus.OK);
    }
}
