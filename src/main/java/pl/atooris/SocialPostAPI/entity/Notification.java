package pl.atooris.SocialPostAPI.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "notifications")
public class Notification {

    public Notification(){
        this.creationDate = LocalDateTime.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    @Column(name = "creation_date")
    LocalDateTime creationDate;

    @Column(name = "is_read")
    boolean isRead = false;

    @Column(name = "content")
    String content;

    @JsonIncludeProperties("username")
    @ManyToMany(mappedBy = "notifications")
    Set<User> receivers;

    @JsonIncludeProperties("username")
    @OneToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    User sender;
}
