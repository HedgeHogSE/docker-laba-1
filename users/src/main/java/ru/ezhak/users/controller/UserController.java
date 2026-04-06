package ru.ezhak.users.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ezhak.users.model.User;
import ru.ezhak.users.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public List<User> getUsers() {
        return userService.findUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") Long id) {
        return userService.findUserById(id);
    }

    @PostMapping
    public void addUser(@RequestBody User user) {
        userService.save(user);
    }

    @DeleteMapping("/{id}")
    public void removeUser(@PathVariable("id") Long id) {
        userService.delete(id);
    }

    @PatchMapping("/{id}")
    public void updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        userService.update(id, user);
    }
}
