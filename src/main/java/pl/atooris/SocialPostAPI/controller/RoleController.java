package pl.atooris.SocialPostAPI.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.atooris.SocialPostAPI.service.RoleService;

@RestController
@AllArgsConstructor
public class RoleController {

    RoleService roleService;

    @PatchMapping("admin/user/{userId}/role/{name}")
    public ResponseEntity<HttpStatus> addRole(@PathVariable Long userId, @PathVariable String name){
        roleService.addRoleToUser(name, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
