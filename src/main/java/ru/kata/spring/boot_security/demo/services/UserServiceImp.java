package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserServiceImp implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImp(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public List<User> getListUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUser(Long id) {
        return userRepository.getById(id);
    }

    @Override
    public String encodePassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    private boolean validUser(User user) {
        return !user.getName().isBlank() &&
                !user.getLastname().isBlank() &&
                !user.getUsername().isBlank() &&
                !user.getPassword().isBlank() &&
                user.getAge() != 0;
    }

    @Transactional
    @Override
    public void saveUser(User user) {
        if (validUser(user)) {
            if (findUserByUsername(user.getUsername()) == null) {
                user.setPassword(encodePassword(user.getPassword()));
                userRepository.save(user);
            }
        }
    }

    @Transactional
    @Override
    public void updateUser(User user, Long id) {
        User updateUser = findUser(id);
        if (validUser(updateUser)) {
            if (user.getPassword().isBlank()) {
                user.setPassword(updateUser.getPassword());
                userRepository.save(user);
            } else {
               // user.setPassword(encodePassword(user.getPassword()));
                userRepository.save(user);
            }
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                user.getAuthorities());
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        userRepository.delete(findUser(id));
    }
}

