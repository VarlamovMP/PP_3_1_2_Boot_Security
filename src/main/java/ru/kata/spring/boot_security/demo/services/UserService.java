package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetails;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {

    User findUserByUsername(String username);

    UserDetails loadUserByUsername(String username);

    void saveUser(User user);

    List<User> getListUsers();

    User findUser(Long id);

    void deleteUser(Long id);

    public void updateUser(User user, Long id);
}
