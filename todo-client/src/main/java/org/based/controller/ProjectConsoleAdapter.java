package org.based.controller;

import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.based.application.ProjectService;
import org.based.domain.Project;
import org.jetbrains.annotations.NotNull;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@Log4j2
public class ProjectConsoleAdapter {
    @NotNull
    private final ProjectService projectService;
    public ProjectConsoleAdapter(@NotNull ProjectService projectService) {
        log.info("ProjectConsoleAdapter initialization");
        this.projectService = projectService;
    }
    @ShellMethod(value = "Finds all projects.")
    @NotNull
    public List<Project> findProjects() {
        log.info("Method findAllProjects projects was called");
        return projectService.findAll();
    }
    @ShellMethod(value = "Finds project by name.")
    @NotNull
    public Project findProject(@NotNull final String name) {
        log.info(String.format("Method findProjectByName was called with arguments: arg1 - %s",
                name));
        return projectService.findByName(name);
    }
    @ShellMethod("Saves new project.")
    public void saveProject(@NotNull final String name, @NotNull final String description) {
        log.info(String.format("Method saveProject was called with arguments: arg1 - %s, arg2 - %s",
                name, description));
        projectService.save(new Project(name, description));
    }
    @ShellMethod("Updates project.")
    private void updateProject(@NotNull final String name, @NotNull final String description) {
        log.info(String.format(
                "Method updateProject was called with arguments: arg1 - %s, arg2 - %s",
                name, description));
        projectService.update(name, description);
    }
    @ShellMethod("Deletes project by name.")
    public void deleteProject(@NotNull final String name) {
        log.info(String.format("Method delete was called with arguments: arg1 - %s", name));
        projectService.deleteByName(name);
    }

}
