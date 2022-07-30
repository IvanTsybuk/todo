package org.based.controller;

import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.based.application.UserService;
import org.based.domain.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Log4j2
public class UserController {
    @NotNull
    private final UserService userService;
    public UserController(@NotNull UserService userService) {
        log.info("TaskController initialization");
        this.userService = userService;
    }
    @GetMapping
    @NotNull
    public List<User> findAll() {
        log.info("Method findAll tasks was called");
        return userService.findAll();
    }
    @GetMapping("/{name}")
    @NotNull
    public User findByName(@PathVariable @NotNull final String name) {
        log.info(String.format("Method findByName was called with arguments: arg1 - %s", name));
        return userService.findByName(name);
    }
    @PostMapping
    public void save(@RequestBody @NotNull final User user) {
        log.info(String.format("Method save was called with arguments: arg1 - %s", user));
        userService.save(user);
    }
    @PutMapping
    private void update(@RequestBody @NotNull final User user) {
        log.info(String.format("Method update was called with arguments: arg1 - %s", user));
        userService.update(user);
    }
    @DeleteMapping("/{name}")
    public void delete(@PathVariable @NotNull final String name) {
        log.info(String.format("Method delete was called with arguments: arg1 - %s", name));
        userService.deleteByName(name);
    }
}
