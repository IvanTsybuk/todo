package org.based.controller;

import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.based.application.TaskService;
import org.based.domain.Task;
import org.jetbrains.annotations.NotNull;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@Log4j2
public class TaskConsoleAdapter {
    @NotNull
    private final TaskService taskService;
    public TaskConsoleAdapter(@NotNull TaskService taskService) {
        log.info("TaskConsoleAdapter initialization");
        this.taskService = taskService;
    }
    @ShellMethod(value = "Finds all tasks.")
    @NotNull
    public List<Task> findTasks() {
        log.info("Method findAllTasks was called");
        return taskService.findAll();
    }
    @ShellMethod(value = "Finds task by name.")
    @NotNull
    public Task findTask(@NotNull final String name) {
        log.info(String.format("Method findTaskByName was called with arguments: arg1 - %s", name));
        return taskService.findByName(name);
    }
    @ShellMethod("Saves new task.")
    public void saveTask(@NotNull final String name, @NotNull final String description) {
        log.info(String.format("Method saveTask was called with arguments: arg1 - %s, arg2 - %s ",
                name, description));
        taskService.save(new Task(name, description));
    }
    @ShellMethod("Updates task.")
    private void updateTask(@NotNull final String name, @NotNull final String description) {
        log.info(String.format("Method updateTask was called with arguments: arg1 - %s, arg2 - %s",
                name, description));
        taskService.update(name, description);
    }
    @ShellMethod("Deletes task by name.")
    public void deleteTask(@NotNull final String name) {
        log.info(String.format("Method deleteTaskByName was called with arguments: arg1 - %s",
                name));
        taskService.deleteByName(name);
    }
}
