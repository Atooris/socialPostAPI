package pl.atooris.SocialPostAPI.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@RequiredArgsConstructor
@Entity
@Table(name = "post")
public class Post{

    public Post(){
        this.creationDate = LocalDateTime.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonIncludeProperties("username")
    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User author;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @NotBlank(message = "Content cannot be blank")
    @NonNull
    @Column(name = "content", nullable = false)
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<Like> likes;

    @JsonIncludeProperties("name")
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "post_hashtag",
            joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id", referencedColumnName = "id")
    )
    private Set<Hashtag> hashtags;
}