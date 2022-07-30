package org.based.persistence;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.based.domain.User;
import org.based.input.HostPathProperties;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Log4j2
public class TodoUserClient implements Repository<User> {
    @NotNull
    private final RestTemplate restTemplate;
    @NotNull
    private final HostPathProperties hostPath;
    public TodoUserClient(@NotNull RestTemplate restTemplate,
                          @NotNull HostPathProperties hostPath) {
        this.hostPath = hostPath;
        this.restTemplate = restTemplate;
        log.info("TodoUserClient initialized");
    }
    @Override
    public void save(@NotNull final User entity) {
        log.debug(String.format("Method save was called with arguments: arg1 - %s", entity));
        HttpEntity<User> request = new HttpEntity<>(entity);
        restTemplate.exchange(hostPath.getUserPath(), HttpMethod.POST, request, User.class);
    }
    @Override
    public void update(@NotNull final User entity) {
        log.debug(String.format("Method update was called with arguments: arg1 - %s", entity));
        HttpEntity<User> request = new HttpEntity<>(entity);
        restTemplate.exchange(hostPath.getUserPath(), HttpMethod.PUT, request, User.class);
    }
    @Override
    @NotNull
    public List<User> findAll() {
        log.debug("Method findAll users was called");
        final User[] users = restTemplate.getForObject(hostPath.getUserPath(), User[].class);
        if (users != null) {
            return Arrays.asList(users);
        }
        return Collections.emptyList();
    }
    @Override
    @NotNull
    public Optional<User> findByName(@NotNull final String name) {
        log.debug(String.format("Method findByName was called with arguments: arg1 - %s", name));
        final User user = restTemplate
                .getForObject(hostPath.getUserPath() + "/{name}", User.class, name);
        if (user != null) {
            return Optional.of(user);
        }
        return Optional.empty();
    }
    @Override
    public void deleteByName(@NotNull final String name) {
        log.debug(String.format("Method deleteByName was called with arguments: arg1 - %s", name));
        restTemplate.delete(hostPath.getUserPath() + "/{name}", name);
    }
}
