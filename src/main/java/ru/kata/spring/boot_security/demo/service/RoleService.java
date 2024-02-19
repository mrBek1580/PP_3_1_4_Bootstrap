package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.entity.Role;

import java.util.Set;

public interface RoleService {
    Role getRoleById(Long id);
    Role getRoleByName(String name);

    Set<Role> getAllRoles();

    Role saveRole(Role role);

    void deleteRoleById(Long id);
    Set<Role> findDyIds(Set<Long> ids);
}
