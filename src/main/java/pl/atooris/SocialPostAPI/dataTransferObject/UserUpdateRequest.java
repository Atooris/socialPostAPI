package pl.atooris.SocialPostAPI.dataTransferObject;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {
    private Optional<String> firstName;
    private Optional<String> lastName;
    private Optional<String> username;
    private Optional<String> email;
}
