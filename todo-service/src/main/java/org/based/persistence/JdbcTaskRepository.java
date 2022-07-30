package org.based.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.based.domain.Task;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class JdbcTaskRepository implements Repository<Task> {
    private static final String select = "SELECT * FROM tasks";
    private static final String selectByName = "SELECT * FROM tasks WHERE name = ?";
    private static final String delete = "DELETE FROM tasks WHERE name = ?";
    private static final String insert = "INSERT INTO tasks (name, description) VALUES (?, ?)";
    private static final String update = "UPDATE tasks SET name = ?, description = ? WHERE id = ?";
    @NotNull
    private final DataSource dataSource;
    public JdbcTaskRepository(@NotNull DataSource dataSource) {
        log.debug("JdbcTaskRepository initialization");
        this.dataSource = dataSource;
    }
    @Override
    @SneakyThrows
    public void save(@NotNull final Task entity) {
        log.debug(String.format("Method save was called with arguments: arg1 - %s", entity));
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(insert)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getDescription());
            preparedStatement.executeUpdate();
        }
    }
    @Override
    @SneakyThrows
    public void update(@NotNull final Task entity) {
        log.debug(String.format("Method update was called with arguments: arg1 - %s", entity));
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(update)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getDescription());
            preparedStatement.setLong(3, entity.getId());
            preparedStatement.executeUpdate();
        }
    }
    @Override
    @SneakyThrows
    @NotNull
    public List<Task> findAll() {
        log.debug("Method findAll tasks was called");
        List<Task> taskList = new ArrayList<>();
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(select);
             final ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                taskList.add(mapToTask(resultSet));
            }
        }
        if (taskList.isEmpty()) {
            return Collections.emptyList();
        }
        return taskList;
    }
    @Override
    @SneakyThrows
    public void deleteByName(@NotNull final String name) {
        log.debug(String.format("Method deleteByName was called with arguments: arg1 - %s", name));
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(delete)) {
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
        }
    }
    @Override
    @SneakyThrows
    @NotNull
    public Optional<Task> findByName(@NotNull final String name) {
        log.debug(String.format("Method findByName was called with arguments: arg1 - %s", name));
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement preparedStatement =
                     connection.prepareStatement(selectByName)) {
            preparedStatement.setString(1, name);
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Task task = mapToTask(resultSet);
                    return Optional.of(task);
                }
            }
        }
        return Optional.empty();
    }
    @SneakyThrows
    @NotNull
    private Task mapToTask(@NotNull final ResultSet resultSet) {
        log.debug(String.format(
                "Method mapToTask was called with arguments: arg1 - %s", resultSet));
        final Task task = new Task();
        task.setId(resultSet.getLong("id"));
        task.setName(resultSet.getString("name"));
        task.setDescription(resultSet.getString("description"));
        return task;
    }
}
