package pl.atooris.SocialPostAPI.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.atooris.SocialPostAPI.entity.Role;
import pl.atooris.SocialPostAPI.entity.User;
import pl.atooris.SocialPostAPI.exception.EntityNotFoundException;
import pl.atooris.SocialPostAPI.repository.RoleRepository;

import javax.management.relation.RoleNotFoundException;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService{

    RoleRepository roleRepository;
    UserService userService;

    @Override
    public Role getRole(String name) {
        Optional<Role> role = roleRepository.findByName(name);
        return unwrapRole(role);
    }

    public static Role unwrapRole(Optional<Role> role){
        if(role.isPresent()) return role.get();
        else throw new EntityNotFoundException(404L, Role.class);
    }

    @Override
    public void addRoleToUser(String name, Long userId) {
        User user = userService.getUser(userId);
        Optional<Role> role = roleRepository.findByName(name);
        Role unwrappedRole = unwrapRole(role);
        user.getRoles().add(unwrappedRole);

    }

    @Override
    public void deleteRoleFromUser(String name, Long userId) {
        User user = userService.getUser(userId);
        Set<Role> userRoles = user.getRoles();
        Optional<Role> role = roleRepository.findByName(name);
        Role unwrappedRole = unwrapRole(role);

        if(userRoles.contains(unwrappedRole)) userRoles.remove(unwrappedRole);
        else throw new EntityNotFoundException(404L, Role.class);
    }
}
