package org.based.application;

import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.based.domain.Project;
import org.based.exceptions.EntityNotFoundException;
import org.based.persistence.Repository;
import org.based.persistence.TodoProjectClient;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ProjectService {
    private static final String ENTITY_NOT_FOUND = "Project with name - %s, not found";
    @NotNull
    private final Repository<Project> projectRepository;
    public ProjectService(@NotNull TodoProjectClient projectRepository) {
        log.debug("ProjectService initialization");
        this.projectRepository = projectRepository;
    }
    public void save(@NotNull final Project project) {
        log.debug(String.format("Method save was called with arguments: arg1 - %s", project));
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
    public void update(@NotNull final String name, @NotNull final String description) {
        log.debug(String.format("Method update was called with arguments: arg1 - %s, arg2 - %s",
                name, description));
        final Project projectForUpdate = projectRepository
                .findByName(name)
                .orElseThrow(() -> {
                    log.error(String.format("Project with name %s not found", name));
                    throw new EntityNotFoundException(
                            String.format(ENTITY_NOT_FOUND, name));
                });
        projectForUpdate.setDescription(description);
        projectRepository.update(projectForUpdate);
    }
}
