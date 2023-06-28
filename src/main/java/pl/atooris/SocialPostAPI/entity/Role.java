package pl.atooris.SocialPostAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Entity
@Table(name = "Role", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Role implements GrantedAuthority {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @NonNull
    @Column(name = "name")
    String name;

    @Override
    public String getAuthority() {
        return this.name;
    }

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
}
