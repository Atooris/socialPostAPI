package pl.atooris.SocialPostAPI.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.atooris.SocialPostAPI.dataTransferObject.UserUpdateRequest;
import pl.atooris.SocialPostAPI.entity.User;
import pl.atooris.SocialPostAPI.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<String> getUser(@PathVariable Long id){
        return new ResponseEntity<>(userService.getUser(id).getUsername(), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<String>> getAllUsers(){
        List<String> usernames = userService.getAllUsers().stream()
                        .map(User::getUsername)
                        .toList();
        return new ResponseEntity<>(usernames, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<String> saveUser(@Valid @RequestBody User user){
        userService.saveUser(user);
        return new ResponseEntity<>(user.getUsername(), HttpStatus.CREATED);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody User user){
        userService.registerUser(user);
        String message = "User \"" + user.getUsername() + "\" created. We sent you a email, please verify your email to complete registration.";
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("{id}/confirm/{token}")
    public ResponseEntity<String> confirmUser(@PathVariable String token, @PathVariable Long id){
        userService.confirmUser(token, id);
        String message = "User successfully verified.";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}/update-user")
    public ResponseEntity<User> updateUser(@RequestBody UserUpdateRequest userUpdateRequest, @PathVariable Long id){
        return new ResponseEntity<>(userService.updateUser(userUpdateRequest, id), HttpStatus.OK);
    }

}
