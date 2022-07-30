package org.based.controller;

import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.based.application.TaskService;
import org.based.domain.Task;
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
@RequestMapping("/tasks")
@Log4j2
public class TaskController {
    @NotNull
    private final TaskService taskService;
    public TaskController(@NotNull TaskService taskService) {
        System.out.println();
        log.info("TaskController initialization");
        this.taskService = taskService;
    }
    @GetMapping
    @NotNull
    public List<Task> findAll() {
        log.info("Method findAll tasks was called");
        return taskService.findAll();
    }
    @GetMapping("/{name}")
    @NotNull
    public Task findByName(@PathVariable @NotNull final String name) {
        log.info(String.format("Method findByName was called with arguments: arg1 - %s", name));
        return taskService.findByName(name);
    }
    @PostMapping
    public void save(@RequestBody @NotNull final Task task) {
        log.info(String.format("Method save was called with arguments: arg1 - %s", task));
        taskService.save(task);
    }
    @PutMapping
    private void update(@RequestBody @NotNull final Task task) {
        log.info(String.format("Method update was called with arguments: arg1 - %s", task));
        taskService.update(task);
    }
    @DeleteMapping("/{name}")
    public void delete(@PathVariable @NotNull final String name) {
        log.info(String.format("Method delete was called with arguments: arg1 - %s", name));
        taskService.deleteByName(name);
    }
}
