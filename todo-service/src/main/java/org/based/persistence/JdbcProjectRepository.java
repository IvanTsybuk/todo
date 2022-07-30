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
import org.based.domain.Project;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class JdbcProjectRepository implements Repository<Project> {
    private static final String select = "SELECT * FROM projects";
    private static final String selectByName = "SELECT * FROM projects WHERE name = ?";
    private static final String delete = "DELETE FROM projects WHERE name = ?";
    private static final String insert = "INSERT INTO projects (name, description) VALUES (?,?)";
    private static final String update = "UPDATE projects"
            + " SET name = ?, description = ? WHERE id = ?";
    @NotNull
    private final DataSource dataSource;
    public JdbcProjectRepository(@NotNull DataSource dataSource) {
        log.debug("JdbcProjectRepository initialization");
        this.dataSource = dataSource;
    }
    @Override
    @SneakyThrows
    public void save(@NotNull final Project entity) {
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
    public void update(@NotNull final Project entity) {
        log.debug(String.format("Method update was called with arguments: arg1 - %s", entity));
        try (final Connection connection  = dataSource.getConnection();
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
    public List<Project> findAll() {
        log.debug("Method findAll projects was called");
        List<Project> projectList = new ArrayList<>();
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(select);
             final ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                projectList.add(mapToProject(resultSet));
            }
        }
        if (projectList.isEmpty()) {
            return Collections.emptyList();
        }
        return projectList;
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
    public Optional<Project> findByName(@NotNull final String name) {
        log.debug(String.format("Method findByName was called with arguments: arg1 - %s", name));
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement preparedStatement =
                     connection.prepareStatement(selectByName)) {
            preparedStatement.setString(1, name);
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Project project = mapToProject(resultSet);
                    return Optional.of(project);
                }
            }
        }
        return Optional.empty();
    }
    @SneakyThrows
    @NotNull
    private Project mapToProject(@NotNull final ResultSet resultSet) {
        log.debug(String.format(
                "Method mapToProject was called with arguments: arg1 - %s", resultSet));
        final Project project = new Project();
        project.setId(resultSet.getLong("id"));
        project.setName(resultSet.getString("name"));
        project.setDescription(resultSet.getString("description"));
        return project;
    }
}
