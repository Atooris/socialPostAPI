package pl.atooris.SocialPostAPI.dataTransferObject;

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
