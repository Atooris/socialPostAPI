package pl.atooris.SocialPostAPI.service;

import pl.atooris.SocialPostAPI.entity.Role;

import java.util.Optional;

public interface RoleService {
    Role getRole(String name);
    void addRoleToUser(String name, Long userId);
}
