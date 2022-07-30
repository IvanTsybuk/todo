package org.based.persistence;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.based.domain.Task;
import org.based.domain.User;
import org.based.input.HostPathProperties;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Log4j2
public class TodoTaskClient implements Repository<Task> {
    @NotNull
    private final RestTemplate restTemplate;
    @NotNull
    private final HostPathProperties hostPath;
    public TodoTaskClient(@NotNull RestTemplate restTemplate,
                          @NotNull HostPathProperties hostPath) {
        this.hostPath = hostPath;
        this.restTemplate = restTemplate;
        log.info("TodoTaskClient initialized");
    }
    @Override
    public void save(@NotNull final Task entity) {
        log.debug(String.format("Method save was called with arguments: arg1 - %s", entity));
        HttpEntity<Task> request = new HttpEntity<>(entity);
        restTemplate.exchange(hostPath.getTaskPath(), HttpMethod.POST, request, User.class);
    }
    @Override
    public void update(@NotNull final Task entity) {
        log.debug(String.format("Method update was called with arguments: arg1 - %s", entity));
        HttpEntity<Task> request = new HttpEntity<>(entity);
        restTemplate.exchange(hostPath.getTaskPath(), HttpMethod.PUT, request, Task.class);
    }
    @Override
    @NotNull
    public List<Task> findAll() {
        log.debug("Method findAll users was called");
        final Task[] tasks = restTemplate.getForObject(hostPath.getTaskPath(), Task[].class);
        if (tasks != null) {
            return Arrays.asList(tasks);
        }
        return Collections.emptyList();
    }
    @Override
    @NotNull
    public Optional<Task> findByName(@NotNull final String name) {
        log.debug(String.format("Method findByName was called with arguments: arg1 - %s", name));
        final Task task = restTemplate.getForObject(
                hostPath.getTaskPath() + "/{name}", Task.class, name);
        if (task != null) {
            return Optional.of(task);
        }
        return Optional.empty();
    }
    @Override
    public void deleteByName(@NotNull final String name) {
        log.debug(String.format("Method deleteByName was called with arguments: arg1 - %s", name));
        restTemplate.delete(hostPath.getTaskPath() + "/{name}", name);
    }
}
