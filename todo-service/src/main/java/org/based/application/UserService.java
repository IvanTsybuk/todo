package org.based.application;

import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.based.domain.User;
import org.based.exceptions.EntityAlreadyExistsException;
import org.based.exceptions.EntityNotFoundException;
import org.based.persistence.Repository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class UserService {
    private static final String ALREADY_EXIST = "User with name - %s, exists";
    private static final String ENTITY_NOT_FOUND = "User with name - %s, not found";
    @NotNull
    private final Repository<User> userRepository;
    public UserService(@NotNull Repository<User> userRepository) {
        log.debug("UserService initialization");
        this.userRepository = userRepository;
    }
    public void save(@NotNull final User user) {
        log.debug(String.format("Method save was called with arguments: arg1 - %s", user));
        userRepository.findByName(user.getName())
                .ifPresent(a -> {
                    log.error(String.format("User - %s already exists", user));
                    throw new EntityAlreadyExistsException(
                            String.format(ALREADY_EXIST, a.getName()));
                });
        userRepository.save(user);
    }
    @NotNull
    public List<User> findAll() {
        log.debug("Method findAll users was called");
        return userRepository.findAll();
    }
    @NotNull
    public User findByName(@NotNull final String name) {
        log.debug(String.format("Method findByName was called with arguments: arg1 - %s", name));
        return userRepository.findByName(name).orElseThrow(
                () -> {
                    log.error(String.format("User with name %s not found", name));
                    throw  new EntityNotFoundException(String.format(ENTITY_NOT_FOUND, name));
                });
    }
    public void deleteByName(@NotNull final String name) {
        log.debug(String.format("Method deleteByName was called with arguments: arg1 - %s", name));
        userRepository.deleteByName(name);
    }
    public void update(@NotNull final User user) {
        log.debug(String.format("Method update was called with arguments: arg1 - %s", user));
        userRepository.findByName(user.getName()).orElseThrow(
                () -> {
                    log.error(String.format("User with name %s not found", user.getName()));
                    throw new EntityNotFoundException(
                            String.format(ENTITY_NOT_FOUND, user.getName()));
                });
        userRepository.update(user);
    }
}
