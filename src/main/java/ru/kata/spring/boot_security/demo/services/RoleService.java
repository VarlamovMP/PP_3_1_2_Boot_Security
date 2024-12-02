package ru.kata.spring.boot_security.demo.services;

import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;

public interface RoleService {
    List<Role> getListRoles();

    Role findRoleByName(String name);

    @Transactional
    void saveRole(Role role);
}
