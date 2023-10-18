package pl.atooris.SocialPostAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @Lob
    @Column(name = "image_data")
    byte[] imageData;

    @Column(name = "image_name")
    String imageName;

    @JsonIncludeProperties("id")
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "post_id")
    private Post post;

    @JsonIncludeProperties("id")
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "user_id")
    private User author;
}
