package org.based.application;

import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.based.domain.Task;
import org.based.exceptions.EntityAlreadyExistsException;
import org.based.exceptions.EntityNotFoundException;
import org.based.persistence.Repository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class TaskService {
    private static final String ALREADY_EXIST = "Task with name - %s, already exists";
    private static final String ENTITY_NOT_FOUND = "Task with name - %s, not found";
    @NotNull
    private final Repository<Task> taskRepository;
    public TaskService(@NotNull Repository<Task> taskRepository) {
        log.debug("TaskService initialization");
        this.taskRepository = taskRepository;
    }
    public void save(@NotNull final Task task) {
        log.debug(String.format("Method save was called with arguments: arg1 - %s", task));
        taskRepository.findByName(task.getName())
                .ifPresent(a -> {
                    log.error(String.format("Task - %s already exists", task));
                    throw new EntityAlreadyExistsException(
                            String.format(ALREADY_EXIST, a.getName()));
                });
        taskRepository.save(task);
    }
    @NotNull
    public List<Task> findAll() {
        log.debug("Method findAll tasks was called");
        return taskRepository.findAll();
    }
    public void deleteByName(@NotNull final String name) {
        log.debug(String.format("Method deleteByName was called with arguments: arg1 - %s", name));
        taskRepository.deleteByName(name);
    }
    @NotNull
    public Task findByName(@NotNull final String name) {
        log.debug(String.format("Method findByName was called with arguments: arg1 - %s", name));
        return taskRepository.findByName(name).orElseThrow(
                () -> {
                    log.error(String.format("Task with name %s not found", name));
                    throw  new EntityNotFoundException(String.format(ENTITY_NOT_FOUND, name));
                });
    }
    public void update(@NotNull final Task task) {
        log.debug(String.format("Method update was called with with arguments: arg1 - %s", task));
        taskRepository.findByName(task.getName()).orElseThrow(
                () -> {
                    log.error(String.format("Task with name %s not found", task.getName()));
                    throw new EntityNotFoundException(
                            String.format(ENTITY_NOT_FOUND, task.getName()));
                });
        taskRepository.update(task);
    }
}
