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
import org.based.domain.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class JdbcUserRepository implements Repository<User> {
    private static final String select = "SELECT * FROM users";
    private static final String selectByName = "SELECT * FROM users WHERE name = ?";
    private static final String delete = "DELETE FROM users WHERE name = ?";
    private static final String insert = "INSERT INTO users (name, surname) VALUES (?, ?)";
    private static final String update = "UPDATE users SET name = ?, surname = ? WHERE id = ?";
    @NotNull
    private final DataSource dataSource;
    public JdbcUserRepository(@NotNull DataSource dataSource) {
        log.debug("JdbcUserRepository initialization");
        this.dataSource = dataSource;
    }
    @Override
    @SneakyThrows
    public void save(@NotNull final User entity) {
        log.debug(String.format("Method save was called with arguments: arg1 - %s", entity));
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(insert)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getSurname());
            preparedStatement.executeUpdate();
        }
    }
    @Override
    @SneakyThrows
    public void update(@NotNull final User entity) {
        log.debug(String.format("Method update was called with arguments: arg1 - %s", entity));
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(update)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getSurname());
            preparedStatement.setLong(3, entity.getId());
            preparedStatement.executeUpdate();
        }
    }
    @Override
    @SneakyThrows
    @NotNull
    public List<User> findAll() {
        log.debug("Method findAll users was called");
        List<User> userList = new ArrayList<>();
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement preparedStatement =
                     connection.prepareStatement(select);
             final ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                userList.add(mapToUser(resultSet));
            }
        }
        if (userList.isEmpty()) {
            return Collections.emptyList();
        }
        return userList;
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
    public Optional<User> findByName(@NotNull final String name) {
        log.debug(String.format("Method findByName was called with arguments: arg1 - %s", name));
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement preparedStatement =
                     connection.prepareStatement(selectByName)) {
            preparedStatement.setString(1, name);
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    User user = mapToUser(resultSet);
                    return Optional.of(user);
                }
            }
        }
        return Optional.empty();
    }
    @SneakyThrows
    @NotNull
    private User mapToUser(@NotNull final ResultSet resultSet) {
        log.debug(String.format(
                "Method mapToUser was called with arguments: arg1 - %s", resultSet));
        final User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setName(resultSet.getString("name"));
        user.setSurname(resultSet.getString("surname"));
        return user;
    }
}
