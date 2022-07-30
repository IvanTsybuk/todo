package org.based.persistence;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.based.domain.Project;
import org.based.input.HostPathProperties;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Log4j2
public class TodoProjectClient implements Repository<Project> {
    @NotNull
    private final RestTemplate restTemplate;
    @NotNull
    private final HostPathProperties hostPathProperties;
    public TodoProjectClient(@NotNull RestTemplate restTemplate,
                             @NotNull HostPathProperties hostPathProperties) {
        log.info("TodoProjectClient initialized");
        this.restTemplate = restTemplate;
        this.hostPathProperties = hostPathProperties;
    }
    @Override
    public void save(@NotNull final Project entity) {
        log.debug(String.format("Method save was called with arguments: arg1 - %s", entity));
        HttpEntity<Project> request = new HttpEntity<>(entity);
        restTemplate.exchange(hostPathProperties.getProjectPath(),
                HttpMethod.POST, request, Project.class);
    }
    @Override
    public void update(@NotNull final Project entity) {
        log.debug(String.format("Method update was called with arguments: arg1 - %s", entity));
        HttpEntity<Project> request = new HttpEntity<>(entity);
        restTemplate.exchange(hostPathProperties.getProjectPath(),
                HttpMethod.PUT, request, Project.class);
    }
    @Override
    @NotNull
    public List<Project> findAll() {
        log.debug("Method findAll users was called");
        final Project[] projects = restTemplate
                .getForObject(hostPathProperties.getProjectPath(), Project[].class);
        if (projects != null) {
            return Arrays.asList(projects);
        }
        return Collections.emptyList();
    }
    @Override
    @NotNull
    public Optional<Project> findByName(@NotNull final String name) {
        log.debug(String.format("Method findByName was called with arguments: arg1 - %s", name));
        final Project project = restTemplate
                .getForObject(hostPathProperties.getProjectPath() + "/{name}", Project.class, name);
        if (project != null) {
            return Optional.of(project);
        }
        return Optional.empty();
    }
    @Override
    public void deleteByName(@NotNull final String name) {
        log.debug(String.format("Method deleteByName was called with arguments: arg1 - %s", name));
        restTemplate.delete(hostPathProperties.getProjectPath() + "/{name}", name);
    }
}
