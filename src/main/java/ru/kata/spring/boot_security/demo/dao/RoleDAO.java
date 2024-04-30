package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.entity.Role;
import java.util.Set;

public interface RoleDAO {
    Role getRoleById(Long id);

    Role getRoleByName(String name);

    Set<Role> getAllRoles();

    Set<Role> findByIds(Set<Long> ids);
}
