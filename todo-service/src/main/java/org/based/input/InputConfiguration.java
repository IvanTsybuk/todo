package org.based.input;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Log4j2
public class InputConfiguration {
    private static final String URL_DEFAULT = "jdbc:postgresql://localhost:5432/todoApp";
    private static final String USER_DEFAULT = "postgres";
    private static final String PASSWORD_DEFAULT = "postgres";
    private static final String DATABASE_URL = "URL";
    private static final String DATABASE_USER = "USER";
    private static final String DATABASE_PASSWORD = "PASSWORD";
    @Bean
    public DataSource createDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(getEnvVariable(DATABASE_URL, URL_DEFAULT));
        dataSource.setUsername(getEnvVariable(DATABASE_USER, USER_DEFAULT));
        dataSource.setPassword(getEnvVariable(DATABASE_PASSWORD, PASSWORD_DEFAULT));
        return dataSource;
    }
    @NotNull
    private String getEnvVariable(@Nullable final String sourceValue,
                                  @NotNull final String defaultValue) {
        String environmentVariable = System.getenv(sourceValue);
        if (environmentVariable == null || environmentVariable.isBlank()) {
            environmentVariable = defaultValue;
        }
        return environmentVariable;
    }
}
