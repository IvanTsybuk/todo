package org.based.controller;

import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.based.application.UserService;
import org.based.domain.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@Log4j2
public class UserConsoleAdapter {
    @NotNull
    private final UserService userService;
    public UserConsoleAdapter(@NotNull UserService userService) {
        log.info("UserConsoleAdapter initialization");
        this.userService = userService;
    }
    @ShellMethod(value = "Finds all users.")
    @NotNull
    public List<User> findUsers() {
        log.info("Method findAllUsers was called");
        return userService.findAll();
    }
    @ShellMethod(value = "Finds user by name.")
    @NotNull
    public User findUser(@NotNull final String name) {
        log.info(String.format("Method findUserByName was called with arguments: arg1 - %s", name));
        return userService.findByName(name);
    }
    @ShellMethod("Saves new user.")
    public void saveUser(@NotNull final String name, @NotNull final String surname) {
        log.info(String.format("Method save was called with arguments: arg1 - %s, arg2 - %s",
                name, surname));
        userService.save(new User(name, surname));
    }
    @ShellMethod("Updates user.")
    private void updateUser(@NotNull final String name, @NotNull final String surname) {
        log.info(String.format("Method updateUser was called with arguments: arg1 - %s, arg2 - %s",
                name, surname));
        userService.update(name, surname);
    }
    @ShellMethod("Deletes user by name.")
    public void deleteUser(@NotNull final String name) {
        log.info(String.format("Method deleteUserByName was called with arguments: arg1 - %s",
                name));
        userService.deleteByName(name);
    }
}
