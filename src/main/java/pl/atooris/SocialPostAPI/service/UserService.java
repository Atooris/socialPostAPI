package pl.atooris.SocialPostAPI.service;

import pl.atooris.SocialPostAPI.dataTransferObject.UserUpdateRequest;
import pl.atooris.SocialPostAPI.entity.User;

import java.util.List;

public interface UserService {
    User getUser(Long id);
    User getUser(String username);
    User saveUser(User user);
    void deleteUser(Long id);
    User updateUser(UserUpdateRequest userUpdateRequest, Long id);
    List<User> getAllUsers();
    void confirmUser(String token, Long id);
    User registerUser(User user);
}
