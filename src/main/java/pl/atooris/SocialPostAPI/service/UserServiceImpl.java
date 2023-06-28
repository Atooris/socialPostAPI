package pl.atooris.SocialPostAPI.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.atooris.SocialPostAPI.dataTransferObject.UserUpdateRequest;
import pl.atooris.SocialPostAPI.entity.User;
import pl.atooris.SocialPostAPI.exception.EntityNotFoundException;
import pl.atooris.SocialPostAPI.mail.EmailService;
import pl.atooris.SocialPostAPI.repository.RoleRepository;
import pl.atooris.SocialPostAPI.repository.UserRepository;
import pl.atooris.SocialPostAPI.security.manager.JWTTokenManager;

import java.util.List;
import java.util.Optional;

import static pl.atooris.SocialPostAPI.security.SecurityConstants.*;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    UserRepository userRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;
    EmailService emailService;
    RoleRepository roleRepository;
    JWTTokenManager jwtTokenManager;

    @Override
    public User getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        return unwrapUser(user, id);
    }

    @Override
    public User getUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return unwrapUser(user, 404L);
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteUserById(id);
    }

    @Override
    public User updateUser(UserUpdateRequest userUpdateRequest, Long id) {
        Optional<User> user = userRepository.findById(id);
        User unwrappedUser = unwrapUser(user, id);

        if (userUpdateRequest.getFirstName() != null && userUpdateRequest.getFirstName().isPresent()) {
            unwrappedUser.setFirstName(userUpdateRequest.getFirstName().get());
        }
        if (userUpdateRequest.getLastName() != null && userUpdateRequest.getLastName().isPresent()) {
            unwrappedUser.setLastName(userUpdateRequest.getLastName().get());
        }
        if (userUpdateRequest.getUsername() != null && userUpdateRequest.getUsername().isPresent()) {
            unwrappedUser.setUsername(userUpdateRequest.getUsername().get());
        }
        if (userUpdateRequest.getEmail() != null && userUpdateRequest.getEmail().isPresent()) {
            unwrappedUser.setEmail(userUpdateRequest.getEmail().get());
        }
        return userRepository.save(unwrappedUser);
    }

    @Override
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User registerUser(User user) {
        saveUser(user);
        emailService.sendVerificationEmail(user.getEmail(), user.getId());
        user.getRoles().add(roleRepository.findByName(ROLE_GUEST).get());
        return user;
    }

    @Override
    public void confirmUser(String token, Long id){
        jwtTokenManager.verifyToken(token);
        User user = getUser(id);
        user.setVerified(true);
        user.getRoles().add(roleRepository.findByName(ROLE_USER).get());
        userRepository.save(user);
    }

    static User unwrapUser(Optional<User> entity, Long id){
        if(entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(id, User.class);
    }
}
