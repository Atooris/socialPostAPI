package pl.atooris.SocialPostAPI.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@RequiredArgsConstructor
@Entity
@Table(name = "likes")
public class Like {

    public Like(){
        this.creationTime = LocalDateTime.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NonNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User author;

    @JsonIncludeProperties("id")
    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yy HH:mm:ss")
    @Column(name = "creation_time")
    private LocalDateTime creationTime;
}
