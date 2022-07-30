package org.based.controller;

import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.based.application.ProjectService;
import org.based.domain.Project;
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
@RequestMapping(value = "/projects")
@Log4j2
public class ProjectController {
    @NotNull
    private final ProjectService projectService;
    public ProjectController(@NotNull ProjectService projectService) {
        log.info("ProjectController initialization");
        this.projectService = projectService;
    }
    @GetMapping
    @NotNull
    public List<Project> findAll() {
        log.info("Method findAll projects was called");
        return projectService.findAll();
    }
    @GetMapping("/{name}")
    @NotNull
    public Project findByName(@PathVariable @NotNull final String name) {
        log.info(String.format("Method findByName was called with arguments: arg1 - %s", name));
        return projectService.findByName(name);
    }
    @PostMapping
    public void save(@RequestBody @NotNull final Project project) {
        log.info(String.format("Method save was called with arguments: arg1 - %s", project));
        projectService.save(project);
    }
    @PutMapping
    private void update(@RequestBody @NotNull final Project project) {
        log.info(String.format("Method update was called with arguments: arg1 - %s", project));
        projectService.update(project);
    }
    @DeleteMapping("/{name}")
    public void delete(@PathVariable @NotNull final String name) {
        log.info(String.format("Method delete was called with arguments: arg1 - %s", name));
        projectService.deleteByName(name);
    }
}
