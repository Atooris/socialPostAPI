package pl.atooris.SocialPostAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Entity
@Table(name = "hashtag", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Hashtag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    @NonNull
    @Size(min = 1, max = 20, message = "Size must be between 1 to 20")
    @Column(name = "name")
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "hashtags", cascade = CascadeType.ALL)
    private Set<Post> posts;
}
