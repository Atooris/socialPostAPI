package pl.atooris.SocialPostAPI.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

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

    @JsonIncludeProperties({"id", "username"})
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

    @JsonIncludeProperties("id")
    @ManyToOne(optional = false)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;

}
