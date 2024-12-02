package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;


import javax.validation.Valid;
import java.util.logging.Logger;

@Controller
@RequestMapping(value = "/admin/")
public class AdminController {

    private final UserService userService;

    private final RoleService roleService;

    private static final Logger LOGGER = Logger.getLogger(AdminController.class.getName());

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String getListUsers(Model model) {
        LOGGER.info("Getting list of users");
        model.addAttribute("list", userService.getListUsers());
        return "admin/list";
    }

    @GetMapping(value = "/edit")
    public String editUser(@RequestParam(value = "id") Long id,
                           Model model) {
        LOGGER.info("Editing user with id: " + id);
        model.addAttribute("roles", roleService.getListRoles());
        model.addAttribute("user", userService.findUser(id));
        return "admin/edit";
    }

    @PostMapping(value = "/update")
    public String updateUser(@RequestParam(value = "id") Long id,  @Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
        LOGGER.info("Updating user with id: " + id);
        if (bindingResult.hasErrors()) {
            // Если есть ошибки, верните пользователя обратно на страницу редактирования
            return "admin/update/";}
        userService.updateUser(user, id);
        return "redirect:/admin/";
    }

    @GetMapping(value = "/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        LOGGER.info("Deleting user with id: " + id);
        userService.deleteUser(id);
        return "redirect:/admin/";
    }

    @GetMapping(value = "/new")
    public String newUser(@ModelAttribute("user") User user, Model model) {
        LOGGER.info("Creating a new user");
        model.addAttribute("roles", roleService.getListRoles());
        return "admin/new_user";
    }

    @PostMapping(value = "/save")
    public String saveUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
        LOGGER.info("Saving a new user");
        if (bindingResult.hasErrors()) {
            // Если есть ошибки, верните пользователя обратно на форму создания
            return "admin/new_user";} // возвращает обратно на страницу создания пользователя
//        String encodedPassword = new BCryptPasswordEncoder(12).encode(user.getPassword());
//        if (!user.getPassword().isBlank()) {
//            user.setPassword(encodedPassword);
//        }
        userService.saveUser(user);
        return "redirect:/admin/";
    }
}


