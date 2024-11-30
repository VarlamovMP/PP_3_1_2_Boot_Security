package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

@Service
public interface UserService {

    User findUserByUsername(String username);

    UserDetails loadUserByUsername(String username);

    void saveUser(User user);

    List<User> getListUsers();

    User findUser(Long id);

    void deleteUser(Long id);

    public void updateUser(User user, Long id);
}
