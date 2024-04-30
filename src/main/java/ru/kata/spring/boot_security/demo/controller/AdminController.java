package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private final UserService userServiceImpl;
    private final RoleService roleServiceImpl;

    @Autowired
    public AdminController(UserService userServiceImpl, RoleService roleServiceImpl) {
        this.userServiceImpl = userServiceImpl;
        this.roleServiceImpl = roleServiceImpl;
    }

    @GetMapping()
    public String getAdminPage(@AuthenticationPrincipal User user,
                               Principal principal,
                               Model model) {
        model.addAttribute("allUsers", userServiceImpl.getAllUsers());
        model.addAttribute("authUser", userServiceImpl.findUserByUsername(principal.getName()));
        model.addAttribute("roles", roleServiceImpl.getAllRoles());
        model.addAttribute("newUser", new User());
        return "admin_page";
    }

    @PostMapping()
    public String addUser(@ModelAttribute("newUser") User user, @RequestParam("roles") Set<Long> roleIds, Model model) {
        Set<Role> roles = roleServiceImpl.findDyIds(roleIds);
        user.setRoles(roles);
        userServiceImpl.saveUser(user);
        return "redirect:/admin";
    }


    @PostMapping("/{id}/edit_user")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") Long id,
                             @RequestParam(required = false) List<Long> roles
    ) {
        Set<Role> userRoles = new HashSet<>();
        if (roles == null) {
            userRoles.add(roleServiceImpl.getRoleByName("ROLE_USER"));
        } else {
            for (Long roleId : roles) {
                userRoles.add(roleServiceImpl.getRoleById(roleId));
            }
        }
        user.setRoles(userRoles);
        userServiceImpl.updateUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/delete_user")
    public String deleteUserById(@PathVariable Long id) {
        userServiceImpl.delete(id);
        return "redirect:/admin";
    }
}
