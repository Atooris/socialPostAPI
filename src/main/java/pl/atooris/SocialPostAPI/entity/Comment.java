package pl.atooris.SocialPostAPI.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import pl.atooris.SocialPostAPI.entity.User;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
@Entity
@Table(name = "comment")
public class Comment {

    public Comment(){
        this.creationTime = LocalDateTime.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonIncludeProperties({"id", "username"}) //not tested if this makes problems
    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User author;

    @NotBlank(message = "Content cannot be blank")
    @NonNull
    @Column(name = "content")
    private String content;

    @Column(name = "creation_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm")
    private LocalDateTime creationTime;

//    @JsonIgnoreProperties("comments")
    @JsonIncludeProperties("id")
    @ManyToOne(optional = false)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;

}
