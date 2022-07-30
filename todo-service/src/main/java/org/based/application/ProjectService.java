package org.based.application;

import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.based.domain.Project;
import org.based.exceptions.EntityAlreadyExistsException;
import org.based.exceptions.EntityNotFoundException;
import org.based.persistence.Repository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ProjectService {
    private static final String ALREADY_EXIST = "Project with name - %s, already exists";
    private static final String ENTITY_NOT_FOUND = "Project with name - %s, not found";
    @NotNull
    private final Repository<Project> projectRepository;
    public ProjectService(@NotNull Repository<Project> projectRepository) {
        log.debug("ProjectService initialization");
        this.projectRepository = projectRepository;
    }
    public void save(@NotNull final Project project) {
        log.debug(String.format("Method save was called with arguments: arg1 - %s", project));
        projectRepository.findByName(project.getName())
                .ifPresent(a -> {
                    log.error(String.format("Project - %s already exists", project));
                    throw new EntityAlreadyExistsException(
                            String.format(ALREADY_EXIST, a.getName()));
                });
        projectRepository.save(project);
    }
    @NotNull
    public List<Project> findAll() {
        log.debug("Method findAll projects was called");
        return projectRepository.findAll();
    }
    public void deleteByName(@NotNull final String name) {
        log.debug(String.format("Method deleteByName was called with arguments: arg1 - %s", name));
        projectRepository.deleteByName(name);
    }
    @NotNull
    public Project findByName(@NotNull final String name) {
        log.debug(String.format("Method findByName was called with arguments: arg1 - %s", name));
        return projectRepository.findByName(name).orElseThrow(
                () -> {
                    log.error(String.format("Project with name %s not found", name));
                    throw  new EntityNotFoundException(String.format(ENTITY_NOT_FOUND, name));
                });
    }
    public void update(@NotNull final Project project) {
        log.debug(String.format("Method update was called with arguments: arg1 - %s", project));
        projectRepository.findByName(project.getName()).orElseThrow(
                () -> {
                    log.error(String.format("Project with name %s not found",
                            project.getName()));
                    throw new EntityNotFoundException(
                        String.format(ENTITY_NOT_FOUND, project.getName()));
                });
        projectRepository.update(project);
    }
}
