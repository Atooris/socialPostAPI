package pl.atooris.SocialPostAPI.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import pl.atooris.SocialPostAPI.validation.Age;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class User{

    public User(){
        this.creationDate = LocalDateTime.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @NotBlank(message = "First name cannot be blank")
    @NonNull
    @Size(min = 2, max = 15)
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @NonNull
    @Size(min = 2, max = 15)
    @Column(name = "last_name")
    private String lastName;

    @NotBlank(message = "Username cannot be blank")
    @NonNull
    @Size(min = 2, max = 15)
    @Column(name = "username")
    private String username;

    @NonNull
    @Size(min = 5)
    @Column(name = "password")
    private String password;

    @Email(message = "Enter a valid email")
    @NonNull
    @Column(name = "email")
    private String email;

    @JsonIgnore
    @Column(name = "verified")
    private boolean isVerified = false;

    @JsonIgnore
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Post> posts;

    @JsonIgnore
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "user_follow",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "follow_id")
    )
    private Set<User> followers;

    @JsonIgnore
    @ManyToMany(mappedBy = "followers")
    private Set<User> following;

    @JsonIgnore
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Like> like;

    @Age(message = "Minimum age is 13")
    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate birthDate;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    Set<Role> roles = new HashSet<>();

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_notification",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "notification_id")
    )
    Set<Notification> notifications = new HashSet<>();

}